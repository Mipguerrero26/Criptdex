package com.pi.criptdex.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
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
import com.pi.criptdex.LoginViewModel
import com.pi.criptdex.R
import com.pi.criptdex.navigation.Screens
import com.pi.criptdex.ui.theme.Teal200
import com.pi.criptdex.ui.theme.Teal500

@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavController) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Login(Modifier.align(Alignment.TopCenter), viewModel, navController)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, navController: NavController) {

    val email : String by viewModel.email.observeAsState(initial = "")
    val password : String by viewModel.password.observeAsState(initial = "")
    val loginEnable:Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading:Boolean by viewModel.isLoading.observeAsState(initial = false)

    if(isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }else {
        Column(modifier = modifier) {

            if (viewModel.showLoginButton.value) {
                HeaderImage(Modifier.align(Alignment.CenterHorizontally))
                HeaderTitle(modifier = Modifier.align(Alignment.CenterHorizontally), title = "Inicio sesión")
                Spacer(modifier = Modifier.padding(4.dp))
                EmailField(email) { viewModel.onLoginChanged(it, password) }
                Spacer(modifier = Modifier.padding(4.dp))
                PasswordField(password) {viewModel.onLoginChanged(email, it)}
                Spacer(modifier = Modifier.padding(8.dp))
                ForgotPassword(Modifier.align(Alignment.End))
                Spacer(modifier = Modifier.padding(16.dp))
                LoginButton(email, password, loginEnable, navController)
                Spacer(modifier = Modifier.padding(8.dp))
                ChangeMode(modifier = Modifier.align(Alignment.CenterHorizontally), viewModel, modo = "Registrarse")
            } else {
                HeaderImage(Modifier.align(Alignment.CenterHorizontally))
                HeaderTitle(modifier = Modifier.align(Alignment.CenterHorizontally), title = "Registro")
                Spacer(modifier = Modifier.padding(4.dp))
                EmailField(email) { viewModel.onLoginChanged(it, password) }
                Spacer(modifier = Modifier.padding(4.dp))
                PasswordField(password) {viewModel.onLoginChanged(email, it)}
                Spacer(modifier = Modifier.padding(16.dp))
                SingUpButton(email, password, loginEnable, navController)
                Spacer(modifier = Modifier.padding(8.dp))
                ChangeMode(modifier = Modifier.align(Alignment.CenterHorizontally), viewModel, modo = "Volvel")
            }
        }
    }
}

@Composable
fun ChangeMode(modifier: Modifier, viewModel: LoginViewModel, modo: String) {
    Text(
        text = modo,
        modifier = modifier.clickable { viewModel.toggleLoginButton() },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Teal500
    )
}

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
                        Toast.makeText(context, "Error, no se ha podido registrar al usuario", Toast.LENGTH_LONG).show()
                    }
                }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = Teal200,
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        enabled = loginEnable
    ) {
        Text(text = "Registrarse")
    }
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
            disabledBackgroundColor = Teal200,
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
        color = Teal500
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

@Composable
fun HeaderTitle(modifier: Modifier, title: String) {
    Text(
        text = title,
        modifier,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Teal500
    )
}