package com.example.halitra

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.halitra.databinding.ActivityMainBinding
import com.example.halitra.ui.CameraXActivity
import com.example.halitra.ui.TextToSpeechActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menggunakan View Binding untuk mengatur layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Mengatur fungsi klik untuk tombol di dalam CardView
        binding.buttonInsideCard.setOnClickListener {
            // Implementasikan aksi yang diinginkan ketika tombol di dalam CardView diklik
            CameraUtils.startCameraXActivity(this) // Perbaikan: Mengganti startCameraActivity menjadi startCameraXActivity
        }

        binding.buttonInsideCard2.setOnClickListener {
            // Implementasikan aksi yang diinginkan ketika tombol di dalam CardView diklik
            startTextToSpeechActivity()
        }


        // Implementasikan logika atau fungsi lain yang diperlukan dalam onCreate()
    }

    private fun startCameraXActivity() { // Perbaikan: Menambahkan fungsi startCameraXActivity yang sesuai
        val cameraIntent = Intent(this, CameraXActivity::class.java)
        startActivity(cameraIntent)
    }
    private fun startTextToSpeechActivity() {
        val intent = Intent(this, TextToSpeechActivity::class.java)
        startActivity(intent)
    }
}
