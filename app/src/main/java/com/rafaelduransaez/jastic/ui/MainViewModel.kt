package com.rafaelduransaez.jastic.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeAt(visiblePermissionDialogQueue.lastIndex)
    }

    fun onPermissionResult(isGranted: Boolean, permission: String) {

    }
}