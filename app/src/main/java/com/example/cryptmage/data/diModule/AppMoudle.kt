package com.example.cryptmage.data.diModule

import com.example.cryptmage.ui.screens.home.HomeScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { HomeScreenViewModel(get()) }

}