package com.pi.criptdex.model.api

import com.pi.criptdex.model.api.pricesapi.Description
import com.pi.criptdex.model.api.pricesapi.Image
import com.pi.criptdex.model.api.pricesapi.MarketData

data class CryptoApi(
    val symbol: String,
    val name: String,
    val description: Description,
    val image: Image,
    val market_data: MarketData
)
