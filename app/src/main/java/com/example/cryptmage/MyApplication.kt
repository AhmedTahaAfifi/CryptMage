package com.example.cryptmage

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.cryptmage.data.di.appModule
import com.example.cryptmage.data.di.roomModule
import com.example.cryptmage.data.repository.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application(), DefaultLifecycleObserver {
    private val sessionManager: SessionManager by inject()

    private var lockJob: Job? = null
    private val LOCK_DELAY = 120_000L

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        this.lockJob?.cancel()
    }

    override fun onCreate() {
        super<Application>.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(appModule + roomModule)
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStop(owner: LifecycleOwner) {
        this.lockJob = ProcessLifecycleOwner.get().lifecycleScope.launch {
            delay(LOCK_DELAY)
            sessionManager.lock()
        }
        super.onStop(owner)
    }
}