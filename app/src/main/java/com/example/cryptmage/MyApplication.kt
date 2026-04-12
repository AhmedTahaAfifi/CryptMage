package com.example.cryptmage

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.cryptmage.data.di.appModule
import com.example.cryptmage.data.di.roomModule
import com.example.cryptmage.data.repository.SessionManager
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application(), DefaultLifecycleObserver {
    private val sessionManager: SessionManager by inject()

    override fun onCreate() {
        super<Application>.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(appModule + roomModule)
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStop(owner: LifecycleOwner) {
        sessionManager.lock()
        super.onStop(owner)
    }
}