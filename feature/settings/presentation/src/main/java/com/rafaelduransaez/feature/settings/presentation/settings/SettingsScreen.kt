package com.rafaelduransaez.feature.settings.presentation.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SettingsSection(navController: NavHostController) {
    SettingsScreen(navController)
}

@Composable
internal fun SettingsScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        for (i in 0..10) {
            GenericScreen(text = "Settings $i") {
                if (i == 10) {
                    // Do nothing
                } else {
                    navController.navigate("Settings/$i")
                }

            }
        }
    }
}

@Composable
internal fun GenericScreen(text: String, onNext: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onNext) {
            Text(text = "Next")
        }
    }
}