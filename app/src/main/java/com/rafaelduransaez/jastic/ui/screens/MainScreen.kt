package com.rafaelduransaez.jastic.ui.screens

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafaelduransaez.jastic.ui.MainViewModel
import com.rafaelduransaez.jastic.ui.components.jContactPicker.JContactPicker
import com.rafaelduransaez.jastic.ui.components.jFloatingActionButton.AddFAB
import com.rafaelduransaez.jastic.ui.components.jFloatingActionButton.LikeFAB
import com.rafaelduransaez.jastic.ui.components.jText.JText
import com.rafaelduransaez.jastic.ui.utils.permissions.PermissionsRequester
import com.rafaelduransaez.jastic.ui.utils.show

@Preview
@Composable
fun MainScreen() {

    val viewModel = viewModel<MainViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    var showContactPicker by rememberSaveable { mutableStateOf(false) }

    if(showContactPicker)
        JContactPicker()

    Scaffold(
        topBar = { MainTopAppBar() },
        floatingActionButton = {
            AddFAB {
                //snackBarHostState.show(coroutineScope, "Add clicked")
                showContactPicker = true
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            PermissionsRequester(getInitialPermissions())
            JasticPointsList(it)
        }
    }
}

@Composable
private fun JasticPointsList(it: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(it)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(3) { index ->
                JasticPoint(index)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar() = TopAppBar(modifier = Modifier.background(Color.Blue), title = { JText() })

@Composable
fun JasticPoint(index: Int) =

    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.small,
//        onClick = { onRepoClicked() }

    ) {
        Row(Modifier.padding(8.dp)) {
            JText(
                text = "Rafa $index",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }

private fun getInitialPermissions() =
    mutableListOf(ACCESS_NOTIFICATION_POLICY, ACCESS_FINE_LOCATION).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) add(POST_NOTIFICATIONS)
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) add(ACCESS_BACKGROUND_LOCATION)
    }




