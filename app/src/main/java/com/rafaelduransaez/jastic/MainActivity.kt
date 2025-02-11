package com.rafaelduransaez.jastic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.jastic.navigation.JasticNavigator
import com.rafaelduransaez.jastic.ui.JasticApp
import com.rafaelduransaez.jastic.ui.rememberJasticAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

/*    @Inject
    lateinit var navigator: JasticNavigator*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JasticTheme {

/*                val navController = rememberNavController()
                LaunchedEffect(Unit) {
                    navigator.setNavController(navController)
                }
                val state = rememberJasticAppState(jasticNavigator = navigator)*/

                JasticApp(/*state*/)
            }
        }
    }
}


