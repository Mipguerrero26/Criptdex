package com.pi.criptdex

sealed class Items_menu(val icon: Int?, val title: String?, val ruta: String){
    object LoginScreen: Items_menu(null, null, "LoginScreen")
    object PantallaPrincipal: Items_menu(null, null, "pantallaPrincipal")
    object Pantalla1: Items_menu(R.drawable.baseline_list_alt_24, "Biblioteca", "pantalla1")
    object Pantalla2: Items_menu(R.drawable.baseline_chat_24, "Foro", "pantalla2")
    object Pantalla3: Items_menu(R.drawable.baseline_account_circle_24, "Usuario", "pantalla3")
}
