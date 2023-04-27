package com.pi.criptdex.navigation

import com.pi.criptdex.R

sealed class Screens(val icon: Int?, val title: String?, val route: String){
    object LoginScreen: Screens(null, null, "loginScreen")
    object AppScreen: Screens(null, null, "appScreen")
    object LibraryScreen: Screens(R.drawable.baseline_list_alt_24, "Biblioteca", "libraryScreen")
    object ForumScreen: Screens(R.drawable.baseline_chat_24, "Foro", "forumScreen")
    object UserScreen: Screens(R.drawable.baseline_account_circle_24, "Usuario", "userScreen")
}
