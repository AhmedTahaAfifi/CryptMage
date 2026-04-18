package com.example.cryptmage.ui.screens.details

interface DetailsInteraction {

    fun onPasswordClick()
    fun onPasswordCopiedClick(password: String)
    fun onEmailCopiedClick(email: String)
    fun onDelete()

}