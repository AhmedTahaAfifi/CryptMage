package com.example.cryptmage.ui.screens.login

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
import com.example.cryptmage.utils.keyDerivation.KeyDerivationUtil
import org.koin.core.parameter.parametersOf

class LoginViewModel(private val vaultManager: VaultManager, private val sessionManager: SessionManager) :
    BaseViewModel<LoginUIState, LoginEffect>(LoginUIState()), LoginInteraction {

    init {
        updateState { it.copy(isVaultCreated = vaultManager.isVaultCreated()) }
    }

    override fun onMasterPasswordChange(password: String) {
        updateState { it.copy(masterPassword = password) }
    }

    override fun onConfirmPasswordChange(password: String) {
        updateState { it.copy(confirmPassword = password) }
    }

    override fun onToggleMasterPasswordVisibility() {
        updateState { it.copy(isMasterPasswordVisible = !it.isMasterPasswordVisible) }
    }

    override fun onToggleConfirmPasswordVisibility() {
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
                    java.util.Arrays.fill(key, 0.toByte())
                }
            },
            onSuccess = { database ->
                sessionManager.database = database
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
                } catch (e: Exception) {
                    throw SaltMissingException()
                } finally {
                    java.util.Arrays.fill(key, 0.toByte())
                }
            },
            onSuccess = { database ->
                sessionManager.database = database
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