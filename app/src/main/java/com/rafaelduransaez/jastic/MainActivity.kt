package com.rafaelduransaez.jastic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.jastic.ui.JasticApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

/*    @Inject
    lateinit var navigator: JasticNavigator*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JasticTheme {
                JasticApp()
            }
        }
    }
}


