package com.example.cryptmage.ui.component.snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface SnackBarController {

    val snackBarState: StateFlow<SnackBarState>

    fun showSnackBar(
        messageId: Int,
        status: SnackBarState.States = SnackBarState.States.Success,
        duration: Long = 1500L,
        scope: CoroutineScope
    )

    fun hideSnackBar()

    companion object {
        val Empty = object : SnackBarController {
            override val snackBarState = MutableStateFlow(SnackBarState())
            override fun showSnackBar(messageId: Int, status: SnackBarState.States, duration: Long, scope: CoroutineScope) {}
            override fun hideSnackBar() {}
        }
    }
}

class SnackBarControllerImpl : SnackBarController {

    private var snackBarJob: Job? = null
    override val snackBarState = MutableStateFlow(SnackBarState())

    override fun showSnackBar(
        messageId: Int,
        status: SnackBarState.States,
        duration: Long,
        scope: CoroutineScope
    ) {
        snackBarJob?.cancel()
        snackBarJob = scope.launch {
            snackBarState.update {
                it.copy(
                    messageId = messageId,
                    state = status,
                    isVisible = true
                )
            }
            delay(duration)
            snackBarState.update { it.copy(isVisible = false) }
        }
    }
    /*override fun showSnackBar(
        message: Int*//*suspend () -> String,*//*
        status: SnackBarState.States,
        duration: Long,
        scope: CoroutineScope
    ) {
        snackBarJob?.cancel()
        snackBarJob = scope.launch {
            val messageText = message
            snackBarState.update {
                it.copy(
                    message = messageText,
                    state = status,
                    isVisible = true
                )
            }
            delay(duration)
            snackBarState.update {
                it.copy(isVisible = false)
            }
        }
    }*/

    override fun hideSnackBar() {
        snackBarJob?.cancel()
        snackBarState.update {
            it.copy(isVisible = false)
        }
    }
}
