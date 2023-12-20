package com.example.halitra.ui.camera

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.halitra.R
import com.example.halitra.databinding.ActivityCameraXBinding
import com.example.halitra.ui.main.MainActivity
import com.example.halitra.ui.textToSpeech.TTSpeechActivity
import com.example.halitra.utils.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat
import java.util.concurrent.Executors

class CameraXActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraXBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private var camera: Camera? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var isFlashEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraXBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSwitchCamera.setOnClickListener {
            switchCamera()
        }

        binding.btnFlash.setOnClickListener {
            toggleFlash()
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        StartCameraClassifier()
        binding.bottomNavigationView.selectedItemId = R.id.camera

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this@CameraXActivity, MainActivity::class.java))

                    true
                }

                R.id.mic -> {

                    startActivity(Intent(this@CameraXActivity, TTSpeechActivity::class.java))
                    true
                }

                else -> false

            }
        }
    }


    private fun switchCamera() {
        cameraSelector =
            if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
            else CameraSelector.DEFAULT_BACK_CAMERA

        StartCameraClassifier()
    }

    private fun toggleFlash() {
        isFlashEnabled = !isFlashEnabled
        setFlash()
    }

    private fun setFlash() {
        if (camera != null) {
            val cameraControl = camera?.cameraControl
            val cameraInfo = camera?.cameraInfo

            if (isFlashEnabled && cameraInfo?.hasFlashUnit() == true) {
                cameraControl?.enableTorch(true)
            } else {
                cameraControl?.enableTorch(false)
            }
        }
    }

    private fun StartCameraClassifier() {
        imageClassifierHelper =
            ImageClassifierHelper(
                context = this,
                imageClassifierListener = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(error: String) {
                        runOnUiThread {
                            Toast.makeText(this@CameraXActivity, error, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                        runOnUiThread {
                            results?.let { it ->
                                if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                    val sortedCategories =
                                        it[0].categories.sortedByDescending { it?.score }
                                    val displayResult =
                                        sortedCategories.joinToString("\n") {
                                            "${it.label} " + NumberFormat.getPercentInstance().format(it.score).trim()
                                        }
                                    binding.tvResult.text = displayResult
                                }
                            }
                        }
                    }
                })

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            val imageAnalyzer =
                ImageAnalysis.Builder()
                    .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                    .setTargetRotation(binding.viewFinder.display.rotation)
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                    .build()
                    .also {
                        it.setAnalyzer(Executors.newSingleThreadExecutor()) { image ->
                            imageClassifierHelper.classify(image)
                        }
                    }

            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageAnalyzer
                )
                setFlash() // Set initial flash state
            } catch (exc: Exception) {
                Toast.makeText(this, "Gagal memunculkan kamera.", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }
}
