package com.pi.criptdex.model.api.pricesapi

data class PricesApi(
    val prices: List<List<Double>>,
    val market_caps: List<List<Double>>,
    val total_volumes: List<List<Double>>
)
