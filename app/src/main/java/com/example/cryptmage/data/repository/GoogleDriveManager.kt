package com.example.cryptmage.data.repository

import android.accounts.Account
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.cryptmage.domain.exception.InvalidCredentialTypeException
import com.google.android.gms.auth.api.identity.AuthorizationRequest
import com.google.android.gms.common.api.Scope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.http.FileContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import java.io.IOException

class GoogleDriveManager(private val context: Context) {

    private val credentialManager = CredentialManager.create(context)

    suspend fun signIn(activityContext: Context): String? {
        val request = getGoogleSignInRequest()
        val result = this.credentialManager.getCredential(activityContext, request)
        val credential = result.credential

        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data)
            val email = googleIdToken.email

            return email
        } else {
            throw InvalidCredentialTypeException()
        }

    }

    fun getGoogleSignInRequest(): GetCredentialRequest {
        val googleInOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("1029183500585-iijjor5bnjf1tbnh6jnkrqpq4a1ntdri.apps.googleusercontent.com")
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleInOption)
            .build()
    }

    fun getDriveService(email: String?): Drive {

        if (email == null) {
            throw IllegalStateException("Email cannot be null")
        }
        val googleAccount = Account(email, "com.google")
        val credential = GoogleAccountCredential.usingOAuth2(
            context, listOf(DriveScopes.DRIVE_APPDATA)
        )
        credential.selectedAccount = googleAccount

        return Drive.Builder(
            NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            credential
        ).setApplicationName("CryptMage").build()
    }

    fun getDriveAuthorizationRequest(): AuthorizationRequest {
        val driveScope = Scope("https://www.googleapis.com/auth/drive.appdata")
        return AuthorizationRequest.Builder()
            .setRequestedScopes(listOf(driveScope))
            .build()
    }

    fun uploadDatabaseFile(email: String?) {
        val driveService = getDriveService(email)
        val dbFile = context.getDatabasePath("cryptmage_dp")

        if (!dbFile.exists()) {
            throw IOException("Database file not found")
        }

        try {
            val fileList = driveService.files().list()
                .setSpaces("appDataFolder")
                .setQ("name = 'cryptmage_backup.dp'")
                .execute()

            val existingFile = fileList.files?.firstOrNull()?.id
            val fileMateDate = File().apply {
                name = "cryptmage_backup.dp"
                if (existingFile == null) {
                    parents = listOf("appDataFolder")
                }
            }
            val mediaContent = FileContent("application/octet-stream", dbFile)

            if (existingFile != null) {
                driveService.files().update(existingFile, fileMateDate, mediaContent).execute()
            } else {
                driveService.files().create(fileMateDate, mediaContent)
                    .setFields("id")
                    .execute()
            }
        } catch (e: UserRecoverableAuthIOException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    fun getBackupCount(email: String?): Int {
        if (email == null) return 0
        return try {
            val driveService = getDriveService(email)
            val result = driveService.files().list()
                .setSpaces("appDataFolder")
                .setQ("name = 'cryptmage_backup.dp'")
                .execute()
            result.files?.size ?: 0
        } catch (e: Exception) {
            throw e
        }
    }

    fun getLastSyncTime(email: String?): Long? {
        if (email == null) return null
        return try {
            val driveService = getDriveService(email)
            val result = driveService.files().list()
                .setSpaces("appDataFolder")
                .setQ("name = 'cryptmage_backup.dp'")
                .setFields("files(modifiedTime)")
                .execute()
            result.files?.firstOrNull()?.modifiedTime?.value
        } catch (_: Exception) {
            null
        }
    }

    fun getDatabaseSize(): String {
        val dbFile = context.getDatabasePath("cryptmage_dp")
        if (!dbFile.exists()) return "0 KB"
        val bytes = dbFile.length()
        if (bytes < 1024) return "$bytes B"
        val kb = bytes / 1024
        if (kb < 1024) return "$kb KB"
        val mb = kb / 1024
        return "$mb MB"
    }

}