package com.example.halitra.ui

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.halitra.MainActivity
import com.example.halitra.R
import com.example.halitra.databinding.ActivityTtspeechBinding
import com.example.halitra.ui.CameraXActivity
import java.util.Locale


class TTSpeechActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTtspeechBinding
    private lateinit var editText: EditText
    private lateinit var imgButton2: ImageButton
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the binding instance
        binding = ActivityTtspeechBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Access UI elements using the binding instance
        editText = binding.editText
        imgButton2 = binding.imgButton2

        // Initialize TextToSpeech engine
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = textToSpeech.setLanguage(Locale.getDefault())

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Handle language not supported
                }
            } else {
                // Handle TextToSpeech initialization failure
            }
        }

        imgButton2.setOnClickListener {
            val textToRead = editText.text.toString()
            if (textToRead.isNotEmpty()) {
                // Speak the text
                textToSpeech.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

        // Set item pertama sebagai item yang aktif secara default
        binding.bottomNavigationView.selectedItemId = R.id.mic

        // Tambahkan listener untuk menanggapi perubahan item yang dipilih
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Tindakan yang akan diambil saat item "Home" dipilih
                    startActivity(Intent(this@TTSpeechActivity, MainActivity::class.java))

                    true
                }
                R.id.camera -> {
                    // Tindakan yang akan diambil saat item "Camera" dipilih
                    startActivity(Intent(this@TTSpeechActivity, CameraXActivity::class.java))

                    true
                }
//                R.id.mic -> {
//                    // Tindakan yang akan diambil saat item "Mic" dipilih
//                    true
//                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Shutdown TextToSpeech engine when the activity is destroyed
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}
