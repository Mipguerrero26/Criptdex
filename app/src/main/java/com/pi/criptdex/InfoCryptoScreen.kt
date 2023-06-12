package com.pi.criptdex

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun InfoCryptoScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar() {
                Icon(
                    imageVector = Icons.Default.ArrowBack, 
                    contentDescription = "Atras",
                modifier = Modifier.clickable { 
                    navController.popBackStack()
                })
                Spacer(modifier = Modifier.width(8.dp))
                Text("Info crypto")
        }
    }) {
        Column() {
            Text("Pagina de informacion",
                textAlign = TextAlign.Center)
        }
    }
    
}