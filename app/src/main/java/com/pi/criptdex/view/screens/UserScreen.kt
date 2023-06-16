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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.pi.criptdex.MainActivity
import com.pi.criptdex.R

//Ventana del usuario
@Composable
fun UserScreen(context: MainActivity){
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userEmail = currentUser?.email

    Column(modifier = Modifier.fillMaxSize()) {
        UserInfo(userEmail)
        SingOffButton(context)
    }
}

//Información del usuario
@Composable
fun UserInfo(userEmail: String?) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.user),
            contentDescription = stringResource(R.string.userImage_text),
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

//Botón de cierre de sesión
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
        Text(text = stringResource(R.string.singof_text))
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

//Ventana de advertencia
@Composable
fun ConfirmationDialog(onConfirm: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(text = stringResource(R.string.warning_text))
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(text = stringResource(R.string.confirmation_text))
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel
            ) {
                Text(text = stringResource(R.string.cancellation_text))
            }
        }
    )
}