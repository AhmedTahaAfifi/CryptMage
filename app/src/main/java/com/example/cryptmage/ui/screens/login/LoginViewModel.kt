package com.example.cryptmage.ui.screens.login

import android.app.Application
import android.content.Context
import android.util.Base64
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewModelScope
import com.example.cryptmage.R
import com.example.cryptmage.data.database.AppDataBase
import com.example.cryptmage.data.repository.SessionManager
import com.example.cryptmage.data.repository.VaultManager
import com.example.cryptmage.domain.exception.InvalidMasterPasswordException
import com.example.cryptmage.domain.exception.SaltMissingException
import com.example.cryptmage.domain.requests.AppRequests
import com.example.cryptmage.ui.component.snackbar.SnackBarState
import com.example.cryptmage.ui.parent.BaseViewModel
import com.example.cryptmage.utils.BiometricManager
import com.example.cryptmage.utils.keyDerivation.KeyDerivationUtil
import org.koin.core.parameter.parametersOf
import java.security.KeyStore
import java.util.Arrays
import javax.crypto.Cipher
import javax.crypto.SecretKey

class LoginViewModel(
    private val application: Application,
    private val vaultManager: VaultManager,
    private val sessionManager: SessionManager,
    private val biometricManager: BiometricManager
) : BaseViewModel<LoginUIState, LoginEffect>(LoginUIState()), LoginInteraction {
    
    private fun getBiometricKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        return keyStore.getKey("biometric_vault_key", null) as SecretKey
    }

    init {
        updateState { it.copy(isVaultCreated = vaultManager.isVaultCreated()) }
    }

    override fun onMasterPasswordChange(password: String) {
        updateState { it.copy(masterPassword = password) }
    }

    override fun onConfirmPasswordChange(password: String) {
        updateState { it.copy(confirmPassword = password) }
    }

    override fun onMasterPasswordClick() {
        updateState { it.copy(isMasterPasswordVisible = !it.isMasterPasswordVisible) }
    }

    override fun onConfirmPasswordClick() {
        updateState { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
    }

    override fun onLogin() {
        val state = viewState.value

        if (state.masterPassword.length < 8) {
            showSnackBar(
                messageId = R.string.error_password_too_short,
                status = SnackBarState.States.Error
            )
            return
        }

        if (state.isVaultCreated) {
            this.authenticate(state.masterPassword)
        } else {
            if (state.masterPassword != state.confirmPassword) {
                showSnackBar(
                    messageId = R.string.error_passwords_do_not_match,
                    status = SnackBarState.States.Error
                )
                return
            }
            this.createVault(state.masterPassword)
        }
    }

    // Helper to store encrypted password
    private fun storeEncryptedPassword(context: Context, password: String) {
        try {
            biometricManager.generateBiometricKey()
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, getBiometricKey())
            val encryptedPassword = cipher.doFinal(password.toByteArray())
            val iv = cipher.iv

            val prefs = context.getSharedPreferences("bio_prefs", Context.MODE_PRIVATE)
            prefs.edit {
                putString("encrypted_password", Base64.encodeToString(encryptedPassword, Base64.DEFAULT))
                putString("iv", Base64.encodeToString(iv, Base64.DEFAULT))
            }
        } catch (e: Exception) {
            android.util.Log.e("Biometric", "Failed to store encrypted password", e)
        }
    }

    // Updated biometric success flow
    override fun onBiometricLogin(activity: FragmentActivity) {
        val canAuth = biometricManager.canAuthenticate()
        android.util.Log.d("Biometric", "canAuthenticate: $canAuth")
        if (!canAuth) {
            showSnackBar(messageId = R.string.something_went_wrong, status = SnackBarState.States.Error)
            return
        }

        val prefs = activity.getSharedPreferences("bio_prefs", Context.MODE_PRIVATE)
        val ivString = prefs.getString("iv", null)
        val encPassword = prefs.getString("encrypted_password", null)

        android.util.Log.d("Biometric", "IV present: ${ivString != null}, EncPassword present: ${encPassword != null}")

        if (ivString == null || encPassword == null) {
            showSnackBar(messageId = R.string.something_went_wrong, status = SnackBarState.States.Error)
            return
        }

        val iv = Base64.decode(ivString, Base64.DEFAULT)
        val cryptoObject = biometricManager.getCryptoObject(iv)
        if (cryptoObject == null) {
             android.util.Log.e("Biometric", "CryptoObject null")
            showSnackBar(
                messageId = R.string.something_went_wrong,
                status = SnackBarState.States.Error
            )
            return
        }

        this.biometricManager.showBiometricPrompt(
            activity,
            cryptoObject,
            onSuccess = { result ->
                result.cryptoObject?.cipher?.let { cipher ->
                    try {
                        val encryptedPassword = Base64.decode(prefs.getString("encrypted_password", ""), Base64.DEFAULT)
                        val decryptedBytes = cipher.doFinal(encryptedPassword)
                        val decryptedPassword = String(decryptedBytes)
                        authenticate(decryptedPassword)
                    } catch (e: Exception) {
                        android.util.Log.e("Biometric", "Decryption failed", e)
                        showSnackBar(
                            messageId = R.string.something_went_wrong,
                            status = SnackBarState.States.Error
                        )
                    }
                }
            },
            onError = { error ->
                android.util.Log.e("Biometric", "Prompt Error: $error")
                showSnackBar(
                    messageId = R.string.something_went_wrong,
                    status = SnackBarState.States.Error
                )
            }
        )
    }
    private fun authenticate(password: String) {
        AppRequests.makeRequest(
            scope = viewModelScope,
            onStarted = { updateState { it.copy(isLoading = true) } },
            onCompleted = { updateState { it.copy(isLoading = false) } },
            request = {
                val salt = vaultManager.getSalt() ?: throw Exception("Salt missing")
                val key = KeyDerivationUtil.deriveKey(password, salt)
                try {
                    val database = getKoin().get<AppDataBase> { parametersOf(key) }

                    database.openHelper.writableDatabase
                    database // return database
                } catch (_: Exception) {
                    throw InvalidMasterPasswordException()
                } finally {
                    Arrays.fill(key, 0.toByte())
                }
            },
            onSuccess = { database ->
                sessionManager.database = database
                val prefs = application.getSharedPreferences("bio_prefs", Context.MODE_PRIVATE)
                if (!prefs.contains("encrypted_password")) {
                    biometricManager.clearBiometricKey()
                    biometricManager.generateBiometricKey()
                    storeEncryptedPassword(application, password)
                }
                sendEffect(LoginEffect.NavigateToHome)
            }, onError = { errorState ->
                showSnackBar(
                    messageId = errorState.messageId,
                    status = SnackBarState.States.Error
                )
            }
        )
    }

    private fun createVault(password: String) {
        AppRequests.makeRequest(
            scope = viewModelScope,
            onStarted = { updateState { it.copy(isLoading = true) } },
            onCompleted = { updateState { it.copy(isLoading = false) } },
            request = {
                val salt = vaultManager.generateNewSalt()
                val key = KeyDerivationUtil.deriveKey(password, salt)
                try {
                    val database = getKoin().get<AppDataBase> { parametersOf(key) }

                    database.openHelper.writableDatabase
                    database // return database
                } catch (_: Exception) {
                    throw SaltMissingException()
                } finally {
                    Arrays.fill(key, 0.toByte())
                }
            },
            onSuccess = { database ->
                sessionManager.database = database
                biometricManager.generateBiometricKey()
                storeEncryptedPassword(application, password)
                sendEffect(LoginEffect.NavigateToHome)
            },
            onError = { errorState ->
                showSnackBar(
                    messageId = errorState.messageId,
                    status = SnackBarState.States.Error
                )
            }
        )
    }

}