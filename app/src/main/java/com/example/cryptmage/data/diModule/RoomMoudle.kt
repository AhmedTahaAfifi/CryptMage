package com.example.cryptmage.data.diModule

import android.annotation.SuppressLint
import androidx.room.Room
import com.example.cryptmage.data.dao.VaultDao
import com.example.cryptmage.data.database.AppDataBase
import com.example.cryptmage.data.repository.VaultRepository
import com.example.cryptmage.data.repository.VaultRepositoryImpl
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {

    // DataBase
    factory<AppDataBase> { (key: ByteArray) ->
        System.loadLibrary("sqlcipher")

        Room.databaseBuilder(
            androidContext(),
            AppDataBase::class.java,
            "cryptmage_dp"
        )
            .openHelperFactory(SupportOpenHelperFactory(key))
            .build()
    }
    /*single {
        Room.databaseBuilder(
            androidContext(),
            AppDataBase::class.java,
            "cryptmage_dp"
        ).build()
    }*/

    // DAO
    single<VaultDao> { get<AppDataBase>().vaultDao() }

    // Repository
    single<VaultRepository> { VaultRepositoryImpl(get()) }

}