package com.andromasters.pixacompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.andromasters.pixacompose.presentation.nav.NavigationScreen
import com.andromasters.pixacompose.presentation.theme.PixabayAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PixabayAppTheme {
                NavigationScreen()
            }
        }
    }
}


