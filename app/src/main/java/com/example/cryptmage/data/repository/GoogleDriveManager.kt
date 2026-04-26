package com.example.cryptmage.data.repository

import android.accounts.Account
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.cryptmage.domain.exception.InvalidCredentialTypeException
import com.example.cryptmage.utils.Constants
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
            .setServerClientId(Constants.Drive.GOOGLE_SERVER_CLIENT_ID)
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
        val driveScope = Scope(Constants.Drive.GOOGLE_DRIVE_URI)
        return AuthorizationRequest.Builder()
            .setRequestedScopes(listOf(driveScope))
            .build()
    }

    fun uploadDatabaseFile(email: String?) {
        val driveService = getDriveService(email)
        val dbFile = context.getDatabasePath(Constants.Drive.APP_DATABASE_PATH)

        if (!dbFile.exists()) {
            throw IOException("Database file not found")
        }

        try {
            val fileList = driveService.files().list()
                .setSpaces(Constants.Drive.APP_DATA_FOLDER)
                .setQ(Constants.Drive.BACKUP_FILE_QUERY)
                .execute()

            val existingFile = fileList.files?.firstOrNull()?.id
            val fileMetadata = File().apply {
                name = Constants.Drive.BACKUP_FILE_NAME
                if (existingFile == null) {
                    parents = listOf(Constants.Drive.APP_DATA_FOLDER)
                }
            }
            val mediaContent = FileContent("application/octet-stream", dbFile)

            if (existingFile != null) {
                driveService.files().update(existingFile, fileMetadata, mediaContent).execute()
            } else {
                driveService.files().create(fileMetadata, mediaContent)
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
                .setSpaces(Constants.Drive.APP_DATA_FOLDER)
                .setQ(Constants.Drive.BACKUP_FILE_QUERY)
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
                .setSpaces(Constants.Drive.APP_DATA_FOLDER)
                .setQ(Constants.Drive.BACKUP_FILE_QUERY)
                .setFields("files(modifiedTime)")
                .execute()
            result.files?.firstOrNull()?.modifiedTime?.value
        } catch (_: Exception) {
            null
        }
    }

    fun getDatabaseSize(): String {
        val dbFile = context.getDatabasePath(Constants.Drive.APP_DATABASE_PATH)
        if (!dbFile.exists()) return "0 KB"
        val bytes = dbFile.length()
        if (bytes < 1024) return "$bytes B"
        val kb = bytes / 1024
        if (kb < 1024) return "$kb KB"
        val mb = kb / 1024
        return "$mb MB"
    }

}