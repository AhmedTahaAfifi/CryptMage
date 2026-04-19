package com.example.cryptmage.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.cryptmage.domain.exception.InvalidCredentialTypeException
import com.google.android.gms.auth.api.identity.AuthorizationRequest
import com.google.android.gms.common.api.Scope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import java.io.IOException
import java.util.Collections

class GoogleDriveManager(private val context: Context) {

    private val credentialManager = CredentialManager.create(context)

    suspend fun signIn(activityContext: Context): String {
        val request = getGoogleSignInRequest()
        val result = this.credentialManager.getCredential(activityContext, request)
        val credential = result.credential

        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data)
            return googleIdToken.id
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

    fun getDriveService(email: String): Drive {
        val credential = GoogleAccountCredential.usingOAuth2(
            context, Collections.singleton(DriveScopes.DRIVE_APPDATA)
        ).apply { selectedAccountName = email }

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

    fun uploadDatabaseFile(driveService: Drive) {
        Log.d("CloudSync", "uploadDatabaseFile: Started")
        val dbFile = context.getDatabasePath("cryptmage_dp")

        if (!dbFile.exists()) {
            Log.e("CloudSync", "uploadDatabaseFile: DB File not found at ${dbFile.absolutePath}")
            throw IOException("Database file not found")
        }

        Log.d("CloudSync", "uploadDatabaseFile: DB File size: ${dbFile.length()} bytes")

        val existingFileId = driveService.files().list()
            .setSpaces("appDataFolder")
            .setQ("name = 'cryptmage_backup.dp'")
            .execute()
            .files
            .firstOrNull()?.id

        val fileMetaDate = File().apply {
            name = "cryptmage_backup.dp"
            if (existingFileId == null) {
                parents = listOf("appDataFolder")
            }
        }

        val mediaContent = FileContent("application/octet-stream", dbFile)

        if (existingFileId != null) {
            driveService.files().update(existingFileId, null, mediaContent).execute()
        } else {
            driveService.files().create(fileMetaDate, mediaContent)
                .setFields("id")
                .execute()
        }
    }

}