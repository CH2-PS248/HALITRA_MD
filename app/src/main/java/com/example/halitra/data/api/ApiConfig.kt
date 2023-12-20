package com.example.halitra.data.api

import com.example.halitra.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiConfig {
    private const val TTS_URL = BuildConfig.TTS_URL
    private const val NEWS_URL = BuildConfig.NEWS_URL

    fun createApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(TTS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(NEWS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getService(): ApiService {
            return getRetrofit().create(ApiService::class.java)
        }
    }
