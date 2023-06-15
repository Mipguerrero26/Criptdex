package com.pi.criptdex.view.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.pi.criptdex.R
import com.pi.criptdex.ui.theme.Vanem
import com.pi.criptdex.view.navigation.Screens

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
                HeaderTitle(modifier = Modifier.align(Alignment.CenterHorizontally), title = stringResource(R.string.login_title))
                Spacer(modifier = Modifier.padding(4.dp))
                EmailField(email) { viewModel.onLoginChanged(it, password) }
                Spacer(modifier = Modifier.padding(4.dp))
                PasswordField(password) {viewModel.onLoginChanged(email, it)}
                Spacer(modifier = Modifier.padding(8.dp))
                ForgotPassword(Modifier.align(Alignment.End))
                Spacer(modifier = Modifier.padding(16.dp))
                LoginButton(email, password, loginEnable, navController)
                Spacer(modifier = Modifier.padding(8.dp))
                ChangeMode(modifier = Modifier.align(Alignment.CenterHorizontally), viewModel, modo = stringResource(R.string.singup_text))
            } else {
                HeaderImage(Modifier.align(Alignment.CenterHorizontally))
                HeaderTitle(modifier = Modifier.align(Alignment.CenterHorizontally), title = stringResource(R.string.singup_title))
                Spacer(modifier = Modifier.padding(4.dp))
                EmailField(email) { viewModel.onLoginChanged(it, password) }
                Spacer(modifier = Modifier.padding(4.dp))
                PasswordField(password) {viewModel.onLoginChanged(email, it)}
                Spacer(modifier = Modifier.padding(16.dp))
                SingUpButton(email, password, loginEnable, navController)
                Spacer(modifier = Modifier.padding(8.dp))
                ChangeMode(modifier = Modifier.align(Alignment.CenterHorizontally), viewModel, modo = stringResource(R.string.return_text))
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
        color = MaterialTheme.colors.primary
    )
}

@Composable
fun SingUpButton(email: String, password: String, loginEnable: Boolean, navController: NavController) {
    val context = LocalContext.current
    val error = stringResource(R.string.singup_error)

    Button(
        onClick = {
            FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        navController.navigate(route = Screens.AppScreen.route)
                    } else {
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    }
                }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = MaterialTheme.colors.secondary,
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        enabled = loginEnable
    ) {
        Text(text = stringResource(R.string.singup_text))
    }
}

@Composable
fun LoginButton(email: String, password: String, loginEnable: Boolean, navController: NavController) {
    val context = LocalContext.current
    val error = stringResource(R.string.login_error)

    Button(
        onClick = {
            FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        navController.navigate(route = Screens.AppScreen.route)
                    } else {
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    }
                }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = MaterialTheme.colors.secondary,
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        enabled = loginEnable
    ) {
        Text(text = stringResource(R.string.login_text))
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(text = stringResource(R.string.forgot_password),
        modifier = modifier.clickable {  },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = stringResource(R.string.password_text)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        maxLines = 1,
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                val eyeIcon: Painter = painterResource(R.drawable.baseline_remove_red_eye_24)
                Icon(
                    painter = eyeIcon,
                    contentDescription = if (passwordVisibility) stringResource(R.string.hide_password) else stringResource(R.string.show_password)
                )
            }
        },
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
        placeholder = { Text(text = stringResource(R.string.email_text)) },
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
        contentDescription = stringResource(R.string.login_text),
        modifier = modifier.size(250.dp)
    )
}

@Composable
fun HeaderTitle(modifier: Modifier, title: String) {
    Text(
        text = title,
        modifier,
        fontFamily = Vanem,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary
    )
}