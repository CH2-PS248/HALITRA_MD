package com.example.halitra.data.api

import com.example.halitra.data.model.ResponseNews
import com.example.halitra.data.model.TextToSpeechRequest
import com.example.halitra.data.model.TextToSpeechResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("text-to-speech")
    fun convertTextToSpeech(@Body request: TextToSpeechRequest): Call<TextToSpeechResponse>

    @GET("top-headlines-health-news")
    fun getNews(): Call<ResponseNews>
}