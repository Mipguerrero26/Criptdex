package com.pi.criptdex.navigation

import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pi.criptdex.AppScreen
import com.pi.criptdex.ForumScreen
import com.pi.criptdex.LibraryScreen
import com.pi.criptdex.LoginScreen
import com.pi.criptdex.LoginViewModel

import com.pi.criptdex.navigation.Screens.*
import com.pi.criptdex.UserScreen

@Composable
fun NavigationHost(navController: NavHostController){
    NavHost(navController = navController,
        startDestination = LoginScreen.route,
    ){
        composable(LoginScreen.route){
            LoginScreen(LoginViewModel(), navController)
        }
        composable(AppScreen.route){
            AppScreen()
        }

    }
}

@Composable
fun AppNavigationHost(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = LibraryScreen.route,
    ){

        composable(LibraryScreen.route){
            LibraryScreen()
        }
        composable(ForumScreen.route){
            ForumScreen()
        }
        composable(UserScreen.route){
            UserScreen()
        }
    }
}