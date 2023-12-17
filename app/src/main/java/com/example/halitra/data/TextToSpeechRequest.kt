package com.example.halitra.data


data class TextToSpeechRequest(val text: String)

data class TextToSpeechResponse(
    val status: String,
    val message: String,
    val data: Voice?
)

data class Voice(
    val url: String
)