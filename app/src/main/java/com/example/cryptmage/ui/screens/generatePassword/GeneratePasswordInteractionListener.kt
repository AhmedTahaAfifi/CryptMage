package com.example.cryptmage.ui.screens.generatePassword

interface GeneratePasswordInteractionListener {

    fun onLengthChange(length: Int)

    fun onVaultNameChange(name: String)

    fun onEmailChange(email: String)

    fun onToggleUpperCase()

    fun onToggleNumbers()

    fun onToggleSymbols()

    fun onToggleAvoidAmbiguous()

    fun onCopy()

    fun onSave()

    fun onRefresh()

}