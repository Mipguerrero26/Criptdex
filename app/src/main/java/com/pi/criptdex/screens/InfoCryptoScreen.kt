package com.pi.criptdex.screens

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pi.criptdex.ApiService
import com.pi.criptdex.infoAPI.CryptoApi
import com.pi.criptdex.infoAPI.PricesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun InfoCryptoScreen(navController: NavHostController, id: String) {

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
            // Utiliza los datos de crypto y prices obtenidos
            // ...
        } catch (e: Exception) {
            println("API call failed: ${e.message}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Info crypto")
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Atrás",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(3.dp)
                    .fillMaxWidth()
            ) {
                cryptoInfo?.let { crypto ->
                    // Mostrar la imagen de la criptomoneda
                    CryptoImage(
                        crypto.image.large,
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(color = Color.White)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Mostrar la información de la criptomoneda
                    CryptoInfo(
                        name = crypto.name,
                        symbol = crypto.symbol,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar el gráfico de líneas
            prices?.prices?.let { priceList ->
                LineChartView(priceList,
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                cryptoInfo?.let { crypto ->
                    // Mostrar el precio en euros centrado
                    Text(
                        text = "${crypto.market_data.current_price.eur} (EUR)",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Description (English):",
                fontWeight = FontWeight.Bold
            )

            cryptoInfo?.let { crypto ->
                // Mostrar la descripción en un LazyColumn
                LazyColumn (
                    modifier = Modifier.padding(top = 3.dp, bottom = 50.dp)
                        ){
                    item {
                        Text(
                            text = crypto.description.en,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun LineChartView(prices: List<List<Double>>, modifier: Modifier = Modifier) {
    val minValue = prices.minOf { it[1] }
    val maxValue = prices.maxOf { it[1] }

    Box(modifier = modifier) {
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val path = Path()
            val paint = Paint()
            paint.color = Color.Blue
            paint.strokeWidth = 5f
            paint.strokeCap = StrokeCap.Round // Establecer los extremos de línea redondeados

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

                //drawCircle(color = Color.Blue, center = Offset(x, y.toFloat()), radius = 5f)
            }

            drawPath(
                path = path,
                color = Color.Blue,
                alpha = 1f,
                style = Stroke(width = 5f),
                colorFilter = null
            )
        }
    }
}