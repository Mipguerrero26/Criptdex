package com.pi.criptdex

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.pi.criptdex.navigation.Screens

@Composable
fun SingUpButton(email: String, password: String, loginEnable: Boolean, navController: NavController) {
    val context = LocalContext.current

    Button(
        onClick = {
                    FirebaseAuth
                        .getInstance()
                        .createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                navController.navigate(route = Screens.AppScreen.route)
                            } else {
                                Toast.makeText(context, "Error, no se ha podido autentificar al usuario", Toast.LENGTH_LONG).show()
                            }
                        }
                  },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF29DAB9),
            disabledBackgroundColor = Color(0xFF75C4B5),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        enabled = loginEnable
    ) {
        Text(text = "Registrarse")
    }
}

@Composable
fun UserNameField(userName:String, onTextFieldChanged:(String) -> Unit) {
    TextField(
        value = userName, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Nombre de usuario") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            backgroundColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Composable
fun Registration(modifier: Modifier, navController: NavController) {
    Text(text = "Registrarse",
        modifier = modifier.clickable { navController.navigate(route = Screens.SingUpScreen.route) },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF29DAB9)
    )
}

@Composable
fun LoginButton(email: String, password: String, loginEnable: Boolean, navController: NavController) {
    val context = LocalContext.current

    Button(
        onClick = {
                    FirebaseAuth
                        .getInstance()
                        .signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                navController.navigate(route = Screens.AppScreen.route)
                            } else {
                                Toast.makeText(context, "Error, no se ha podido autentificar al usuario", Toast.LENGTH_LONG).show()
                            }
                        }
                  },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF29DAB9),
            disabledBackgroundColor = Color(0xFF75C4B5),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        enabled = loginEnable
    ) {
        Text(text = "Iniciar sesión")
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(text = "¿Olvidaste la contraseña?",
        modifier = modifier.clickable {  },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF29DAB9)
    )
}

@Composable
fun PasswordField(password:String, onTextFieldChanged:(String) -> Unit) {
    TextField(
        value = password, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            backgroundColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun EmailField(email:String, onTextFieldChanged:(String) -> Unit) {
    TextField(
        value = email, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            backgroundColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo2),
        contentDescription = "Header",
        modifier = modifier.size(250.dp)
    )
}