package com.example.halitra.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.halitra.R
import com.example.halitra.databinding.ActivityMainBinding
import com.example.halitra.ui.camera.CameraXActivity
import com.example.halitra.ui.news.NewsAdapter
import com.example.halitra.ui.textToSpeech.TTSpeechActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val news = findViewById<RecyclerView>(R.id.listRV)
        mainViewModel.getNewsData(
            onSuccess = { dataNews ->
                val newsAdapter = NewsAdapter(dataNews)
                news.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    setHasFixedSize(true)
                    newsAdapter.notifyDataSetChanged()
                    adapter = newsAdapter
                }
            },
            onFailure = { errorMessage ->
                Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
            }
        )


        binding.buttonInsideCard.setOnClickListener {
            startCameraXActivity()
        }

        binding.buttonInsideCard2.setOnClickListener {
            startTextToSpeechActivity()
        }
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {

                    startActivity(Intent(this@MainActivity, MainActivity::class.java))

                    true
                }
                R.id.camera -> {

                    startActivity(Intent(this@MainActivity, CameraXActivity::class.java))

                    true
                }
                R.id.mic -> {

                    startActivity(Intent(this@MainActivity, TTSpeechActivity::class.java))
                    true
                }
                else -> false
            }
        }


    }

    private fun startCameraXActivity() {
        val cameraIntent = Intent(this, CameraXActivity::class.java)
        startActivity(cameraIntent)
    }
    private fun startTextToSpeechActivity() {
        val intent = Intent(this, TTSpeechActivity::class.java)
        startActivity(intent)
    }
}
