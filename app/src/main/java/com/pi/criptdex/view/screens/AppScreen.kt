package com.pi.criptdex.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pi.criptdex.MainActivity
import com.pi.criptdex.ui.theme.Vanem
import com.pi.criptdex.view.navigation.Screens.*
import com.pi.criptdex.view.navigation.AppNavigationHost
import com.pi.criptdex.view.navigation.Screens

@Composable
fun AppScreen(context: MainActivity) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    val navigation_item = listOf(
        LibraryScreen,
        ForumScreen,
        UserScreen
    )

    Scaffold (
        scaffoldState = scaffoldState,
        bottomBar = { NavegacionInferior(navController, navigation_item) }
    ){
        Box(modifier = Modifier.padding(it)) {
            AppNavigationHost(context, navController)
        }

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
                                contentDescription = item.title?.let { stringResource(it) } ?: ""
                            )
                        }
                    },
                    label = {
                        item.title?.let { stringResource(it) }?.let {
                            Text(
                                text = it,
                                fontFamily = Vanem
                            )
                        }
                    }
                )
            }
        }
    }
}