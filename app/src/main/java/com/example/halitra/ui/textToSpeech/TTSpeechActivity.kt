package com.example.halitra.ui.textToSpeech

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.halitra.ui.main.MainActivity
import com.example.halitra.R
import com.example.halitra.databinding.ActivityTtspeechBinding
import com.example.halitra.ui.camera.CameraXActivity
import java.util.Locale


class TTSpeechActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTtspeechBinding
    private lateinit var editText: EditText
    private lateinit var imgButton2: ImageButton
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTtspeechBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        editText = binding.editText
        imgButton2 = binding.imgButton2


        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = textToSpeech.setLanguage(Locale.getDefault())

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                }
            } else {

            }
        }

        imgButton2.setOnClickListener {
            val textToRead = editText.text.toString()
            if (textToRead.isNotEmpty()) {

                textToSpeech.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }


        binding.bottomNavigationView.selectedItemId = R.id.mic


        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {

                    startActivity(Intent(this@TTSpeechActivity, MainActivity::class.java))

                    true
                }
                R.id.camera -> {

                    startActivity(Intent(this@TTSpeechActivity, CameraXActivity::class.java))

                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}
