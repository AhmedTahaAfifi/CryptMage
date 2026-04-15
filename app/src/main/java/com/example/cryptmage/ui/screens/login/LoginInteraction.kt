package com.example.cryptmage.ui.screens.login

interface LoginInteraction {

    fun onMasterPasswordChange(password: String)

    fun onMasterPasswordClick()

    fun onConfirmPasswordChange(password: String)

    fun onConfirmPasswordClick()

    fun onLogin()

}