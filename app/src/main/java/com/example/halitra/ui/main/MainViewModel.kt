package com.example.halitra.ui.main


import androidx.lifecycle.ViewModel
import com.example.halitra.data.api.ApiConfig
import com.example.halitra.data.model.ArticlesItem
import com.example.halitra.data.model.ResponseNews
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    fun getNewsData(
        onSuccess: (List<ArticlesItem>?) -> Unit,
        onFailure: (String) -> Unit
    ) {
        ApiConfig.getService().getNews().enqueue(object : Callback<ResponseNews> {
            override fun onResponse(call: Call<ResponseNews>, response: Response<ResponseNews>) {
                if (response.isSuccessful) {
                    val responseNews = response.body()
                    val dataNews = responseNews?.articles
                    onSuccess.invoke(dataNews as List<ArticlesItem>?)
                }
            }

            override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                onFailure.invoke(t.localizedMessage ?: "Unknown error")
            }
        })
    }
}