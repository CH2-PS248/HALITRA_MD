package com.example.halitra

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.halitra.ui.CameraXActivity

object CameraUtils {

    fun startCameraXActivity(context: Context) {
        val cameraIntent = Intent(context, CameraXActivity::class.java)
        context.startActivity(cameraIntent)
    }

    // Tambahkan fungsi-fungsi utilitas lainnya jika diperlukan
}
