package com.pi.criptdex.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.pi.criptdex.MainActivity
import com.pi.criptdex.R

@Composable
fun UserScreen(context: MainActivity){

    val currentUser = FirebaseAuth.getInstance().currentUser
    val userEmail = currentUser?.email

    Column(modifier = Modifier.fillMaxSize()) {
        UserInfo(userEmail)
        SingOffButton(context)
    }
}

@Composable
fun UserInfo(userEmail: String?) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.user),
            contentDescription = "Imagen de usuario",
            modifier = Modifier
                .size(110.dp)
                .padding(end = 8.dp)
        )
        Text(
            text = "$userEmail",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
    }
}

@Composable
fun SingOffButton(context: MainActivity) {
    val showDialog = remember { mutableStateOf(false) }

    Button(
        onClick = { showDialog.value = true },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(start = 20.dp, end = 20.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = "Cerrar sesión")
    }

    if (showDialog.value) {
        ConfirmationDialog(
            onConfirm = {
                FirebaseAuth.getInstance().signOut()
                context.finish()
            },
            onCancel = { showDialog.value = false }
        )
    }
}

@Composable
fun ConfirmationDialog(onConfirm: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(text = "¿Estás seguro de que quieres cerrar sesión? Se te cerrará también la aplicación")
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(text = "Confirmar")
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel
            ) {
                Text(text = "Cancelar")
            }
        }
    )
}