package com.example.cryptmage.data.diModule

import androidx.room.Room
import com.example.cryptmage.data.dao.VaultDao
import com.example.cryptmage.data.database.AppDataBase
import com.example.cryptmage.data.repository.VaultRepository
import com.example.cryptmage.data.repository.VaultRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {

    // DataBase
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDataBase::class.java,
            "cryptmage_dp"
        ).build()
    }

    // DAO
    single<VaultDao> { get<AppDataBase>().vaultDao() }

    // Repository
    single<VaultRepository> { VaultRepositoryImpl(get()) }

}