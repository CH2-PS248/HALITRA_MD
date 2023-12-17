
package com.example.halitra.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.EditText
import android.widget.ImageButton
import com.example.halitra.R
import java.util.Locale

class TextToSpeechActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var imgButton2: ImageButton
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_to_speech)

        editText = findViewById(R.id.editText)
        imgButton2 = findViewById(R.id.imgButton2)

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
    }

    override fun onDestroy() {
        super.onDestroy()
        // Shutdown TextToSpeech engine when the activity is destroyed
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}