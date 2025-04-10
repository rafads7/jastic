package com.rafaelduransaez.base.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class JasticViewModel<NavState> : ViewModel() {

    private val _navState = Channel<NavState>()
    val navState = _navState.receiveAsFlow()

    open fun navigateTo(navDestination: NavState) {
        viewModelScope.launch {
            _navState.send(navDestination)
        }
    }
}