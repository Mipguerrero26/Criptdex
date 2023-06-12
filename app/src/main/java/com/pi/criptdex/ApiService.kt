package com.pi.criptdex

import com.pi.criptdex.infoAPI.CryptoApi
import com.pi.criptdex.infoAPI.PricesApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("coins/{cryptoId}")
    suspend fun getInfoCrypto(
        @Path("cryptoId") cryptoId: String
    ): CryptoApi

    @GET("coins/{cryptoId}/market_chart")
    suspend fun getPrices(
        @Path("cryptoId") cryptoId: String,
        @Query("vs_currency") currency: String = "eur",
        @Query("days") days: Int = 31,
        @Query("interval") interval: String = "daily"
    ): PricesApi
}