package com.example.halitra.api

import com.example.halitra.data.TextToSpeechRequest
import com.example.halitra.data.TextToSpeechResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("text-to-speech")
    fun convertTextToSpeech(@Body request: TextToSpeechRequest): Call<TextToSpeechResponse>
}