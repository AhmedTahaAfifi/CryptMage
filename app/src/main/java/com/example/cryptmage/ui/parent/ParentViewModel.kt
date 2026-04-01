package com.example.cryptmage.ui.parent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

abstract class ParentViewModel<VIEW_STATE>(initialState: VIEW_STATE): ViewModel(), KoinComponent {

    private val _viewState = MutableStateFlow<AppStateFlow>(AppStateFlow.TopBarTitle(""))
    val viewState = _viewState.asStateFlow()


    private fun changeViewState(newViewState: AppStateFlow) {
        viewModelScope.launch {
            _viewState.value = newViewState
        }
    }

}