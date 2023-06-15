package com.pi.criptdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.pi.criptdex.view.navigation.NavigationHost
import com.pi.criptdex.ui.theme.CriptdexTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CriptdexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    var showLoadScreen by remember { mutableStateOf(true) }

                    // Launch a coroutine to wait for 3 seconds before hiding the load screen
                    val lifecycleOwner = LocalLifecycleOwner.current
                    lifecycleOwner.lifecycleScope.launch{
                        delay(3000) // Wait for 3 seconds
                        showLoadScreen = false
                    }

                    // Show the load screen or the navigation host based on the value of showLoadScreen
                    if (showLoadScreen) {
                        LoadScreen()
                    } else {
                        NavigationHost(context= this, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun LoadScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo1),
            contentDescription = stringResource(R.string.load_text)
        )
    }
}