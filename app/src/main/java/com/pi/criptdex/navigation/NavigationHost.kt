package com.pi.criptdex.navigation

import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pi.criptdex.screens.AppScreen
import com.pi.criptdex.screens.ForumScreen
import com.pi.criptdex.screens.LibraryScreen
import com.pi.criptdex.screens.InfoCryptoScreen
import com.pi.criptdex.screens.LoginScreen
import com.pi.criptdex.LoginViewModel
import com.pi.criptdex.MainActivity

import com.pi.criptdex.navigation.Screens.*
import com.pi.criptdex.screens.UserScreen

@Composable
fun NavigationHost(context:MainActivity, navController: NavHostController){
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
        composable(route = "${InfoCryptoScreen.route}/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if (id != null) {
                InfoCryptoScreen(navController = navController, id = id)
            }
        }
        composable(ForumScreen.route){
            ForumScreen()
        }
        composable(UserScreen.route){
            UserScreen(context, navController)
        }

    }
}