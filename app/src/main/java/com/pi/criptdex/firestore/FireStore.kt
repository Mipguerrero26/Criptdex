package com.pi.criptdex.firestore

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.pi.criptdex.model.Crypto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

//Recoge las cryptos de la base de datos y las guarda en una lista
class FireStore {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getCryptos(): List<Crypto> = withContext(Dispatchers.IO) {
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
                }
        }catch (e: FirebaseFirestoreException) {
            println(e)
        }

        cryptoList
    }
}