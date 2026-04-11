package com.example.cryptmage.ui.screens.login

interface LoginInteraction {

    fun onMasterPasswordChange(password: String)

    fun onToggleMasterPasswordVisibility()

    fun onConfirmPasswordChange(password: String)

    fun onToggleConfirmPasswordVisibility()

    fun onLogin()

}