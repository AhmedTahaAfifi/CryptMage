package com.example.cryptmage.data.di

import androidx.room.Room
import com.example.cryptmage.data.dao.VaultDao
import com.example.cryptmage.data.database.AppDataBase
import com.example.cryptmage.data.repository.SessionManager
import com.example.cryptmage.data.repository.VaultRepository
import com.example.cryptmage.data.repository.VaultRepositoryImpl
import com.example.cryptmage.domain.usecases.DeleteVaultEntryUseCase
import com.example.cryptmage.domain.usecases.GetVaultEntryUseCase
import com.example.cryptmage.domain.usecases.InsertVaultEntryUseCase
import com.example.cryptmage.domain.usecases.UpdateVaultEntryUseCase
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {


    // DataBase
    factory<AppDataBase> { (key: ByteArray) ->
        System.loadLibrary("sqlcipher")

        val key = key.copyOf()

        val factory = SupportOpenHelperFactory(key)
        Room.databaseBuilder(
            androidContext(),
            AppDataBase::class.java,
            "cryptmage_dp"
        )
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .build()
    }

    // DAO
    factory<VaultDao> {
        val session: SessionManager = get()
        session.database?.vaultDao() ?: throw IllegalArgumentException("Database not unlocked")
    }

    // Repository
    factory<VaultRepository> { VaultRepositoryImpl(get()) }

    factory { GetVaultEntryUseCase(get()) }
    factory { DeleteVaultEntryUseCase(get()) }
    factory { InsertVaultEntryUseCase(get()) }
    factory { UpdateVaultEntryUseCase(get()) }

}