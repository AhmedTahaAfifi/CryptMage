package com.example.cryptmage.data.repository

import android.content.Context
import java.io.File

class BackupManager(
    private val context: Context,
    private val sessionManager: SessionManager
) {

    fun prepareBackupFile(): File {
        val dbFile = context.getDatabasePath("cryptmage_dp")
        
        // Ensure database is flushed
        sessionManager.database?.let { db ->
            db.query("PRAGMA wal_checkpoint(FULL)", null).use { it.moveToFirst() }
        }

        val exportFile = File(context.cacheDir, "cryptmage_backup.dp")
        dbFile.copyTo(exportFile, overwrite = true)
        
        return exportFile
    }

}
