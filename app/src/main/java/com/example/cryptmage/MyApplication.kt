package com.example.cryptmage

import android.app.Application
import com.example.cryptmage.data.diModule.appModule
import com.example.cryptmage.data.diModule.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(appModule + roomModule)
        }
    }
}