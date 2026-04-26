package com.example.cryptmage.ui.screens.login

import androidx.fragment.app.FragmentActivity

interface LoginInteraction {

    fun onMasterPasswordChange(password: String)

    fun onMasterPasswordClick()

    fun onConfirmPasswordChange(password: String)

    fun onConfirmPasswordClick()

    fun onLogin()

    fun onBiometricLogin(activity: FragmentActivity)

}