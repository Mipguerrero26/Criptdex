package com.pi.criptdex.infoAPI

data class CryptoApi(
    val symbol: String,
    val name: String,
    val description: Description,
    val image: Image,
    val market_data: MarketData
)
