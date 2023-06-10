package com.pi.criptdex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun LibraryScreen() {
    val fireStore = FireStore()
    var cryptoList by remember { mutableStateOf(emptyList<Crypto>()) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val obtainedCryptos = fireStore.obtenerCryptos()
        cryptoList = obtainedCryptos
    }

    Column() {
        TextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
            },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        LazyColumn(modifier = Modifier.padding(top= 3.dp, bottom = 55.dp)) {
            items(cryptoList.filter { crypto ->
                crypto.name.contains(searchQuery, ignoreCase = true)
            }) { crypto ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                ) {
                    Row(modifier = Modifier.padding(4.dp)) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(color = Color.White)
                        ) {
                            AsyncImage(
                                model = crypto.image,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(text = crypto.name, fontWeight = FontWeight.Bold)
                            Text(text = crypto.symbol)
                        }
                    }
                }
            }
        }
    }

}