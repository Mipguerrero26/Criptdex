package com.pi.criptdex.screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.pi.criptdex.navigation.Screens.*
import com.pi.criptdex.navigation.AppNavigationHost
import com.pi.criptdex.navigation.Screens

@Composable
fun AppScreen() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    val navigation_item = listOf(
        LibraryScreen,
        ForumScreen,
        UserScreen
    )

    Scaffold (
        scaffoldState = scaffoldState,
        bottomBar = {NavegacionInferior(navController, navigation_item)}
    ){
        AppNavigationHost(navController)
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val entrada by navController.currentBackStackEntryAsState()
    return entrada?.destination?.route
}

@Composable
fun NavegacionInferior(
    navController: NavHostController,
    menu_items : List<Screens>
) {
    BottomAppBar() {
        BottomNavigation() {
            val currentRoute = currentRoute(navController = navController)
            menu_items.forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.route,
                    onClick = { navController.navigate(item.route) },
                    icon = {
                        item.icon?.let { painterResource(id = it) }?.let {
                            Icon(
                                painter = it,
                                contentDescription = item.title
                            )
                        }
                    },
                    label= { item.title?.let { Text(it) } }
                )
            }
        }
    }
}