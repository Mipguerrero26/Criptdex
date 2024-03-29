package com.pi.criptdex.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.pi.criptdex.model.Crypto
import com.pi.criptdex.firestore.FireStore
import com.pi.criptdex.R
import com.pi.criptdex.ui.theme.Vanem
import com.pi.criptdex.view.navigation.Screens

//Ventana de la biblioteca
@Composable
fun LibraryScreen(navController: NavHostController) {
    val fireStore = FireStore()
    var cryptoList by remember { mutableStateOf(emptyList<Crypto>()) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var isDescendingOrder by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val obtainedCryptos = fireStore.getCryptos()
        cryptoList = obtainedCryptos
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearching) {
                        CryptoSearch(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { query -> searchQuery = query },
                            onSearchAction = { isSearching = false },
                            onCancelSearch = { isSearching = false }
                        )
                    } else {
                        Text(text = stringResource(R.string.libraryScreen_title), fontFamily = Vanem)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { isSearching = true }
                    ) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search_text),
                            tint = MaterialTheme.colors.onSurface
                        )
                    }

                    IconButton(
                        onClick = { isDescendingOrder = !isDescendingOrder }
                    ) {
                        val iconPainter = painterResource(R.drawable.baseline_filter_list_24)

                        Image(
                            painter = iconPainter,
                            contentDescription = stringResource(R.string.filter_text),
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(if (isDescendingOrder) 0f else 180f),
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
                        )
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .padding(8.dp)
        ) {
            val sortedCryptoList = if (isDescendingOrder) cryptoList.sortedBy { it.name } else cryptoList.sortedByDescending { it.name }

            CryptoList(cryptoList = sortedCryptoList, searchQuery = searchQuery, navController = navController)
        }
    }
}

//Buscador cryptomonedas
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CryptoSearch(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchAction: () -> Unit,
    onCancelSearch: () -> Unit
) {
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = { Text(stringResource(R.string.search_text)) },
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    onSearchAction()
                    true
                } else {
                    false
                }
            },
        textStyle = TextStyle(fontSize = 16.sp),
        leadingIcon = {
            IconButton(onClick = {
                onSearchQueryChange("")
                onCancelSearch()
            }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_text)
                )
            }
        }
    )
}

//Lista cryptomonedas
@Composable
fun CryptoList(
    cryptoList: List<Crypto>,
    searchQuery: String,
    navController: NavHostController
) {
    val filteredCryptoList = cryptoList.filter { crypto -> crypto.name.contains(searchQuery, ignoreCase = true)}

    LazyColumn {
        items(filteredCryptoList) { crypto ->
            CryptoCard(image = crypto.image, name = crypto.name, symbol = crypto.symbol, id = crypto.id, navController = navController)
        }
    }
}

//Trajeta-ficha ccryptomoneda
@Composable
fun CryptoCard(image: String, name: String, symbol: String, id: String, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(4.dp)
            )
    ) {
        Row(modifier = Modifier
            .padding(4.dp)
            .clickable {
                navController.navigate(route = "${Screens.CryptoInfoScreen.route}/$id")
            }
        ) {
            CryptoImage(image = image, modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(color = Color.White))

            CryptoInfo(
                name = name,
                symbol = symbol,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
                    .align(Alignment.CenterVertically))
        }
    }
}

//Imagen cryptomoneda
@Composable
fun CryptoImage(image: String, modifier: Modifier) {
    Box(
        modifier
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

//Información sobre que cryptomoneda es
@Composable
fun CryptoInfo(name: String, symbol: String, modifier: Modifier) {
    Column(
        modifier
    ) {
        Text(
            text = name,
            fontFamily = Vanem,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )

        Text(symbol)
    }
}