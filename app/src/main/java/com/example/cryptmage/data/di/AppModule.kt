package com.example.cryptmage.data.di

import com.example.cryptmage.data.repository.VaultManager
import com.example.cryptmage.ui.component.snackbar.SnackBarController
import com.example.cryptmage.ui.component.snackbar.SnackBarControllerImpl
import com.example.cryptmage.ui.screens.details.DetailsViewModel
import com.example.cryptmage.ui.screens.generatePassword.GeneratePasswordViewModel
import com.example.cryptmage.ui.screens.home.HomeViewModel
import com.example.cryptmage.ui.screens.login.LoginViewModel
import com.example.cryptmage.utils.ClipboardManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single<SnackBarController> { SnackBarControllerImpl() }
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::GeneratePasswordViewModel)
    viewModelOf(::DetailsViewModel)

    single { VaultManager(androidContext()) }
    single { ClipboardManager(androidContext()) }



}