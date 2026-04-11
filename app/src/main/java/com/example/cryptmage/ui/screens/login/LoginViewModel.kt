package com.example.cryptmage.ui.screens.login

import com.example.cryptmage.ui.parent.BaseViewModel
import com.example.cryptmage.ui.screens.login.LoginInteraction

class LoginViewModel :
    BaseViewModel<LoginUIState, LoginEffect>(LoginUIState()), LoginInteraction {

    override fun onMasterPasswordChange(password: String) {
        updateState { it.copy(masterPassword = password) }
    }

    override fun onToggleMasterPasswordVisibility() {
        updateState { it.copy(isMasterPasswordVisible = !it.isMasterPasswordVisible) }
    }

    override fun onConfirmPasswordChange(password: String) {
        updateState { it.copy(confirmPassword = password) }
    }

    override fun onToggleConfirmPasswordVisibility() {
        updateState { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
    }

    override fun onLogin() {
        TODO("Not yet implemented")
    }

}