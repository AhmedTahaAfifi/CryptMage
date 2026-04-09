package com.example.cryptmage.ui.screens.generatePassword

import android.content.Context

interface GeneratePasswordInteractionListener {

    fun onLengthChang(length: Int)

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