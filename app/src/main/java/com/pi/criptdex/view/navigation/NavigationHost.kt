package com.pi.criptdex.view.navigation

import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pi.criptdex.MainActivity
import com.pi.criptdex.view.screens.AppScreen
import com.pi.criptdex.view.screens.ForumScreen
import com.pi.criptdex.view.screens.LibraryScreen
import com.pi.criptdex.view.screens.CryptoInfoScreen
import com.pi.criptdex.view.screens.login.LoginScreen
import com.pi.criptdex.view.screens.login.LoginViewModel
import com.pi.criptdex.view.navigation.Screens.*
import com.pi.criptdex.view.screens.UserScreen

@Composable
fun NavigationHost(context: MainActivity, navController: NavHostController){
    NavHost(navController = navController,
        startDestination = LoginScreen.route,
    ){
        composable(LoginScreen.route){
            LoginScreen(LoginViewModel(), navController)
        }
        composable(AppScreen.route){
            AppScreen(context)
        }

    }
}

@Composable
fun AppNavigationHost(context: MainActivity, navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = LibraryScreen.route,
    ){

        composable(LibraryScreen.route){
            LibraryScreen(navController)
        }
        composable(route = "${CryptoInfoScreen.route}/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if (id != null) {
                CryptoInfoScreen(navController = navController, id = id)
            }
        }
        composable(ForumScreen.route){
            ForumScreen()
        }
        composable(UserScreen.route){
            UserScreen(context)
        }

    }
}