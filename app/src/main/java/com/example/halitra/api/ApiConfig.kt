package com.example.halitra.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiConfig {
    private const val BASE_URL = "https://backend-etfbbmn23a-as.a.run.app/"

    fun createApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}