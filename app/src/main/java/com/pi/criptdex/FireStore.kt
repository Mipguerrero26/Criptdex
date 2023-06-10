package com.pi.criptdex

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FireStore {
    private val db = FirebaseFirestore.getInstance()

    suspend fun obtenerCryptos(): List<Crypto> = withContext(Dispatchers.IO) {
        val cryptoList = mutableStateListOf<Crypto>()

        try {
            val snapshot = db.collection("cryptos").get().await()
                for (document in snapshot.documents) {
                    val id = document.getString("id") ?: ""
                    val symbol = document.getString("symbol") ?: ""
                    val name = document.getString("name") ?: ""
                    val image = document.getString("image") ?: ""
                    val cryptos = Crypto(id, symbol, name, image)
                    cryptoList.add(cryptos)
                    System.out.println("bien")
                }
        }catch (e: FirebaseFirestoreException) {
            System.out.println( "mal")
        }

        cryptoList
    }
}