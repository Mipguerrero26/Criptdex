package com.pi.criptdex.infoAPI

data class PricesApi(
    val prices: List<List<Double>>,
    val market_caps: List<List<Double>>,
    val total_volumes: List<List<Double>>
)
