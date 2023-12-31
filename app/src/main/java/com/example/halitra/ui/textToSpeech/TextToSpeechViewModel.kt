package com.example.halitra.ui.textToSpeech

// TextToSpeechViewModel.kt
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import com.example.halitra.data.api.ApiConfig
import com.example.halitra.data.model.TextToSpeechRequest
import com.example.halitra.data.model.TextToSpeechResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TextToSpeechViewModel : ViewModel(), Observable {
    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    @get:Bindable
    var textToSpeechRequest = TextToSpeechRequest("")
        set(value) {
            field = value
            callbacks.notifyCallbacks(this, 0, null)
        }

    fun convertTextToSpeech() {
        val apiService = ApiConfig.createApiService()
        val call = apiService.convertTextToSpeech(textToSpeechRequest)

        call.enqueue(object : Callback<TextToSpeechResponse> {
            override fun onResponse(call: Call<TextToSpeechResponse>, response: Response<TextToSpeechResponse>) {
                if (response.isSuccessful) {
                    // Lakukan sesuatu dengan respons, misalnya menampilkan URL suara
                     val voiceUrl = response.body()?.data?.url
                    // ...
                } else {
                    // Tangani respons yang tidak berhasil
                }
            }

            override fun onFailure(call: Call<TextToSpeechResponse>, t: Throwable) {
                // Tangani kegagalan koneksi atau respons
            }
        })
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }
}
