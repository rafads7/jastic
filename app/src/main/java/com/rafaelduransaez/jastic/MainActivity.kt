package com.rafaelduransaez.jastic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rafaelduransaez.jastic.ui.screens.MainScreen
import com.rafaelduransaez.jastic.ui.theme.JasticTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JasticTheme {
                MainScreen()
            }
        }
    }
}

@Preview
@Composable
fun PreviewScreen() {
    JasticTheme {
        MainScreen()
    }
}


