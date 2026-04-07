package com.example.cryptmage.data.appViewStates.generatePassword

import com.example.cryptmage.data.moudels.generatePassword.GeneratePasswordData

data class GeneratePasswordViewState(
    val data: GeneratePasswordData = GeneratePasswordData(),
    val errorMessageId: Int? = null
)