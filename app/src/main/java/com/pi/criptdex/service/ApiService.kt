package com.pi.criptdex.service

import com.pi.criptdex.model.api.Message
import com.pi.criptdex.model.api.CryptoApi
import com.pi.criptdex.model.api.pricesapi.PricesApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //Información cryptomoneda (coingecko)
    @GET("coins/{cryptoId}")
    suspend fun getInfoCrypto(
        @Path("cryptoId") cryptoId: String
    ): CryptoApi

    //información fluctuación criptomoneda a lo largo de 31 días (coingecko)
    @GET("coins/{cryptoId}/market_chart")
    suspend fun getPrices(
        @Path("cryptoId") cryptoId: String,
        @Query("vs_currency") currency: String = "eur",
        @Query("days") days: Int = 31,
        @Query("interval") interval: String = "daily"
    ): PricesApi

    //mensajes (realtime datsabase)
    @GET("forum.json")
    suspend fun getMessages(): Map<String, Message>
}