package com.pi.criptdex.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ForumScreen(){
    Column(modifier = Modifier.fillMaxSize()) {
        // LazyColumn ocupa casi toda la pantalla
        LazyColumn(
            modifier = Modifier.weight(1f),
            content = {
                // Aquí agregas los elementos que deseas mostrar en el LazyColumn
            }
        )

        // TextField al final de la pantalla
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f),
                value = "",
                onValueChange = { /* Actualiza el valor del TextField */ },
                label = { Text("Escribe tu mensaje") }
            )

            IconButton(
                onClick = { /* Lógica del botón enviar */ }
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Enviar"
                )
            }
        }
    }
}