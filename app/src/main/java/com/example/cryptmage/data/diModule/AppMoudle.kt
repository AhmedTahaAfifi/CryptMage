package com.example.cryptmage.data.diModule

import com.example.cryptmage.ui.component.snackbar.SnackBarController
import com.example.cryptmage.ui.component.snackbar.SnackBarControllerImpl
import com.example.cryptmage.ui.screens.generatePassword.GeneratePasswordViewModel
import com.example.cryptmage.ui.screens.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<SnackBarController> { SnackBarControllerImpl() }
    viewModel { HomeViewModel(get()) }
    viewModel { GeneratePasswordViewModel(get()) }
}