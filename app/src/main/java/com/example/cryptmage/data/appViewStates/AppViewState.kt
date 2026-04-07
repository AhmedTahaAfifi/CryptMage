package com.example.cryptmage.data.appViewStates

sealed class AppViewState {

    data class Success<T>(val data: T): AppViewState()

    data class Error(val message: String): AppViewState()

}