package com.rafaelduransaez.jastic.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafaelduransaez.domain.components.common.empty
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.MainViewModel
import com.rafaelduransaez.jastic.ui.components.jFloatingActionButton.AddFAB
import com.rafaelduransaez.jastic.ui.components.jFloatingActionButton.LikeFAB
import com.rafaelduransaez.jastic.ui.components.jIcon.JIcon
import com.rafaelduransaez.jastic.ui.components.jText.JText
import com.rafaelduransaez.jastic.ui.components.permissions.AccompanistPermissionRequester
import com.rafaelduransaez.jastic.ui.utils.launchSnackbar
import com.rafaelduransaez.jastic.ui.utils.show

@Preview
@Composable
fun MainScreen() {

    val viewModel = viewModel<MainViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { MainTopAppBar() },
        floatingActionButton = {
            AddFAB { snackBarHostState.show(coroutineScope, "Add clicked") }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        AccompanistPermissionRequester()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
/*            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(10) { index ->
                    CheckPoint(index)
                }
            }*/
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar() = TopAppBar(title = { JText() })

@Composable
fun CheckPoint(index: Int) =

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
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            LikeFAB()
        }
    }




