package com.pi.criptdex

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.pi.criptdex.Items_menu.*

@Composable
fun PantallaPrincipal() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    val navigation_item = listOf(
        Pantalla1,
        Pantalla2,
        Pantalla3
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
    menu_items : List<Items_menu>
) {
    BottomAppBar() {
        BottomNavigation() {
            val currentRoute = currentRoute(navController = navController)
            menu_items.forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.ruta,
                    onClick = { navController.navigate(item.ruta) },
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