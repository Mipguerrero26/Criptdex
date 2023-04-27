package com.pi.criptdex

import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.pi.criptdex.Items_menu.*

@Composable
fun NavigationHost(navController: NavHostController){
    NavHost(navController = navController,
        startDestination = LoginScreen.ruta,
    ){
        composable(LoginScreen.ruta){
            LoginScreen(LoginViewModel(), navController)
        }
        composable(PantallaPrincipal.ruta){
            PantallaPrincipal()
        }

    }
}

@Composable
fun AppNavigationHost(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Pantalla1.ruta,
    ){

        composable(Pantalla1.ruta){
            Biblioteca()
        }
        composable(Pantalla2.ruta){
            Foro()
        }
        composable(Pantalla3.ruta){
            Usuario()
        }
    }
}