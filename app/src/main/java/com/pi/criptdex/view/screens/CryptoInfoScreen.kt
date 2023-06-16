package com.pi.criptdex.view.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pi.criptdex.R
import com.pi.criptdex.service.ApiService
import com.pi.criptdex.model.api.CryptoApi
import com.pi.criptdex.model.api.pricesapi.PricesApi
import com.pi.criptdex.ui.theme.Teal500
import com.pi.criptdex.ui.theme.Vanem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Ventana de información de la cryptomoneda
@Composable
fun CryptoInfoScreen(navController: NavHostController, id: String) {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.coingecko.com/api/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)
    var cryptoInfo: CryptoApi? by remember { mutableStateOf(null) }
    var prices: PricesApi? by remember { mutableStateOf(null) }

    LaunchedEffect(id) {
        try {
            val cryptoResponse = withContext(Dispatchers.IO) {
                apiService.getInfoCrypto(id ?: "")
            }
            cryptoInfo = cryptoResponse

            val pricesResponse = withContext(Dispatchers.IO) {
                apiService.getPrices(id ?: "")
            }
            prices = pricesResponse
        } catch (e: Exception) {
            println("API call failed: ${e.message}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.cryptoInfo_title),
                        fontFamily = Vanem
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.return_text),
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )
                }
            )
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            CryptoInfoHeader(cryptoInfo)
            Spacer(Modifier.height(16.dp))
            CryptoGraph(prices)
            Spacer(Modifier.height(16.dp))
            CryptoPrice(cryptoInfo)
            Spacer(Modifier.height(16.dp))
            CryptoDescriptionItem(cryptoInfo)
        }
    }
}

//Identificador cryptomoneda
@Composable
fun CryptoInfoHeader(cryptoInfo: CryptoApi?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
    ) {
        cryptoInfo?.let { crypto ->
            CryptoImage(
                crypto.image.large,
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .width(16.dp)
                    .background(Color.White)
            )

            CryptoInfo(
                name = crypto.name,
                symbol = crypto.symbol,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

//Gráfica de la fluctuación de la cryptomoneda
@Composable
fun CryptoGraph(prices: PricesApi?) {
    prices?.prices?.let { priceList ->
        GraphView(priceList, Modifier.fillMaxWidth().height(100.dp))
    }
}

//Precio de la cryptomoneda
@Composable
fun CryptoPrice(cryptoInfo: CryptoApi?) {
    cryptoInfo?.let { crypto ->
        Text(
            text = "${crypto.market_data.current_price.eur} (EUR)",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(textAlign = TextAlign.Center),
            fontWeight = FontWeight.Bold
        )
    }
}

//Cuerpo de la descripción de la cryptomoneda
@Composable
fun CryptoDescriptionItem(cryptoInfo: CryptoApi?) {
    Text(
        text = stringResource(R.string.description_text),
        fontFamily = Vanem,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary
    )

    cryptoInfo?.let { crypto ->
        CryptoDescription(crypto.description.en)
    }
}

//Descripción de la cryptomoneda
@Composable
fun CryptoDescription(description: String) {
    LazyColumn (
        modifier = Modifier.padding(top = 3.dp)
    ){
        item {
            Text(
                text = description,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

//Gráfica
@Composable
fun GraphView(prices: List<List<Double>>, modifier: Modifier = Modifier) {
    val minValue = prices.minOf { it[1] }
    val maxValue = prices.maxOf { it[1] }

    Box(modifier = modifier) {
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val path = Path()
            val paint = Paint()

            paint.color = Teal500
            paint.strokeWidth = 5f
            paint.strokeCap = StrokeCap.Round

            val xStep = canvasWidth / (prices.size - 1).toFloat()
            val yStep = canvasHeight / (maxValue - minValue).toFloat()

            prices.forEachIndexed { index, price ->
                val x = index * xStep
                val y = canvasHeight - ((price[1] - minValue) * yStep)

                if (index == 0) {
                    path.moveTo(x, y.toFloat())
                } else {
                    val prevX = (index - 1) * xStep
                    val prevY = canvasHeight - ((prices[index - 1][1] - minValue) * yStep)
                    val midX = (prevX + x) / 2

                    path.lineTo(prevX, prevY.toFloat())
                    path.cubicTo(midX, prevY.toFloat(), midX, y.toFloat(), x, y.toFloat())
                }

                //drawCircle(color = Teal500, center = Offset(x, y.toFloat()), radius = 5f)
            }

            drawPath(
                path = path,
                color = Teal500,
                alpha = 1f,
                style = Stroke(width = 5f),
                colorFilter = null
            )
        }
    }
}