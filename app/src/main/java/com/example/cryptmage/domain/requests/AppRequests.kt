package com.example.cryptmage.domain.requests

import com.example.cryptmage.domain.exception.AppException
import com.example.cryptmage.domain.exception.SomeThingWentWrongException
import com.example.cryptmage.ui.parent.AppErrorState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object AppRequests {

    fun <T> makeRequest(
        request: suspend () -> T,
        onSuccess: (suspend (T) -> Unit)? = null,
        onError: (AppErrorState) -> Unit = {},
        onStarted: () -> Unit = {},
        onCompleted: () -> Unit = {},
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        scope: CoroutineScope,
        delayMillis: Long = 0L
    ): Job {
        val handler = CoroutineExceptionHandler { _, throwable ->
            onError(exceptionToErrorState(throwable))
        }

        return scope.launch(dispatcher + handler) {
            onStarted()
            delay(delayMillis)
            runCatching { request() }
                .onSuccess { onSuccess?.invoke(it) }
                .onFailure { onError(exceptionToErrorState(it)) }
            onCompleted()
        }
    }

    private fun exceptionToErrorState(throwable: Throwable): AppErrorState {
        val appException = throwable as? AppException ?: SomeThingWentWrongException()

        return AppErrorState(
            messageId = appException.messageId,
            exception = appException
        )
    }

}