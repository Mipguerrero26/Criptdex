package com.pi.criptdex.view.navigation

import com.pi.criptdex.R

sealed class Screens(val icon: Int?, val title: Int?, val route: String){
    object LoginScreen: Screens(null, null, "loginScreen")
    object AppScreen: Screens(null, null, "appScreen")
    object LibraryScreen: Screens(R.drawable.baseline_list_alt_24, R.string.libraryScreen_title, "libraryScreen")
    object CryptoInfoScreen: Screens(null, null, "cryptoInfoScreen/{id}")
    object ForumScreen: Screens(R.drawable.baseline_chat_24, R.string.forumScreen_title, "forumScreen")
    object UserScreen: Screens(R.drawable.baseline_account_circle_24, R.string.userScreen_title, "userScreen")
}
