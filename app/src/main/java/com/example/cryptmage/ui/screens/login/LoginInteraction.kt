package com.example.cryptmage.ui.screens.login

interface LoginInteraction {

    fun onMasterPasswordChange(password: String)

    fun onMasterPasswordClick()

    fun onConfirmPasswordChange(password: String)

    fun onConfirmPasswordClick()

    fun onLogin()
    fun onBiometricLogin(activity: androidx.fragment.app.FragmentActivity)

}