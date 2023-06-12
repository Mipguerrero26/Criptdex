package com.pi.criptdex.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pi.criptdex.EmailField
import com.pi.criptdex.HeaderImage
import com.pi.criptdex.PasswordField
import com.pi.criptdex.SingUpButton
import com.pi.criptdex.SingUpViewModel
import com.pi.criptdex.UserNameField

@Composable
fun SingUpScreen(viewModel: SingUpViewModel, navController: NavController) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SingUp(Modifier.align(Alignment.TopCenter), viewModel, navController)
    }
}

@Composable
fun SingUp(modifier: Modifier, viewModel: SingUpViewModel, navController: NavController) {

    val userName : String by viewModel.userName.observeAsState(initial = "")
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
            HeaderImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = modifier.padding(14.dp))
            UserNameField(userName) { viewModel.onLoginChanged(it, email, password) }
            Spacer(modifier = Modifier.padding(4.dp))
            EmailField(email) { viewModel.onLoginChanged(userName, it, password) }
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(password) {viewModel.onLoginChanged(userName, email, it)}
            Spacer(modifier = Modifier.padding(16.dp))
            SingUpButton(email, password, loginEnable, navController)
        }
    }

}