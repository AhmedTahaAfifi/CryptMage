package com.example.cryptmage.utils.keyDerivation

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.InvalidAlgorithmParameterException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import android.util.Base64
import androidx.core.content.edit
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator
import org.bouncycastle.crypto.params.KeyParameter
import java.io.IOException
import java.security.InvalidKeyException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.GCMParameterSpec

object KeyDerivationUtil {

    private const val KEY_ALIAS = "cryptmage_salt_encryption_key"
    private const val SALT_PREFS_NAME = "salt_storage_prefs"
    private const val ENCRYPTED_SALT_KEY = "encrypted_db_salt"
    private const val IV_KEY = "db_salt_iv"
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val AES_MODE = "AES/GCM/NoPadding"
    private const val GCM_TAG_LENGTH = 128 // bits
    private const val AES_KEY_SIZE = 256 // bits

    private lateinit var keyStore: KeyStore

    private fun initKeyStore() {
        if(!::keyStore.isInitialized) {
            this.keyStore = KeyStore.getInstance(this.ANDROID_KEYSTORE)
            this.keyStore.load(null)
            if (!this.keyStore.containsAlias(this.KEY_ALIAS)) {
                generateNewSecretKey()
            }
        }
    }

    private fun generateNewSecretKey() {
        try {
            val keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                this.ANDROID_KEYSTORE
            )
            val keyGeneratorSpec = KeyGenParameterSpec.Builder(
                this.KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(this.AES_KEY_SIZE)
                .build()

            keyGenerator.init(keyGeneratorSpec)
            keyGenerator.generateKey()
        } catch (e: Exception) {
            when (e) {
                is NoSuchAlgorithmException,
                    is NoSuchProviderException,
                        is InvalidAlgorithmParameterException -> {
                            throw RuntimeException("Failed to generate secret key.", e)
                }
                else -> throw e
            }
        }
    }

    private fun getSecretKey(): SecretKey {
        initKeyStore()
        return this.keyStore.getKey(this.KEY_ALIAS, null) as SecretKey
    }

    private fun getSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(this.SALT_PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun generateAndSaveSalt(context: Context): ByteArray {
        val salt = ByteArray(16) // 128-bit salt
        SecureRandom().nextBytes(salt)

        val cipher = Cipher.getInstance(this.AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val encryptedSalt = cipher.doFinal(salt)
        val iv = cipher.iv

        val prefs = this.getSharedPrefs(context)
        prefs.edit {
            putString(ENCRYPTED_SALT_KEY, Base64.encodeToString(encryptedSalt, Base64.DEFAULT))
                .putString(IV_KEY, Base64.encodeToString(iv, Base64.DEFAULT))
        }

        return salt
    }

    fun getSalt(context: Context): ByteArray? {
        val prefs = this.getSharedPrefs(context)
        val encryptedSaltString = prefs.getString(this.ENCRYPTED_SALT_KEY, null)
        val ivString = prefs.getString(this.IV_KEY, null)

        if (encryptedSaltString == null || ivString == null) return null

        val encryptedSalt = Base64.decode(encryptedSaltString, Base64.DEFAULT)
        val iv = Base64.decode(ivString, Base64.DEFAULT)

        return try {
            val cipher = Cipher.getInstance(this.AES_MODE)
            val spec = GCMParameterSpec(this.GCM_TAG_LENGTH, iv)

            cipher.init(Cipher.DECRYPT_MODE, this.getSecretKey(), spec)
            cipher.doFinal(encryptedSalt)
        } catch (e: Exception) {
            when (e) {
                is NoSuchAlgorithmException, is NoSuchPaddingException,
                    is InvalidKeyException, is InvalidAlgorithmParameterException,
                        is IOException -> {
                            // IOException can happen with doFinal
                            // Log error and consider clearing corrupted sald data
                            e.printStackTrace()
                            null
                        }
                else -> throw e
            }
        }
    }

    fun deriveKey(masterPassword: String, salt: ByteArray): ByteArray {
        // Using BouncyCastle's PKCS5S2 for PBKDF2
        val generator = PKCS5S2ParametersGenerator()
        // 10,000 iterations
        generator.init(masterPassword.toByteArray(Charsets.UTF_8), salt, 10000)
        // 256-bit key
        val keyParameter = generator.generateDerivedParameters(this.AES_KEY_SIZE) as KeyParameter

        return keyParameter.key
    }

    fun clearSalt(context: Context) {
        this.getSharedPrefs(context).edit {
            remove(ENCRYPTED_SALT_KEY)
            remove(IV_KEY).apply()
        }
    }

}
