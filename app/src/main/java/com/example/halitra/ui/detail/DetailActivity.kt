package com.example.halitra.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.example.halitra.R


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val title = intent.getStringExtra("title")
        val imageUrl = intent.getStringExtra("imageUrl")
        val url = intent.getStringExtra("url")


        val imgNews = findViewById<ImageView>(R.id.imgNews)
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvUrl = findViewById<TextView>(R.id.tvUrl)


        Glide.with(this)
            .load(imageUrl)
            .error(R.drawable.iconhalitra) // Replace with your placeholder image
            .into(imgNews)


        tvTitle.text = title
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Open the browser with the provided URL
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            }
        }


        val spannableString = SpannableString(url)
        if (url != null) {
            spannableString.setSpan(clickableSpan, 0, url.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        // Set the SpannableString to the TextView
        tvUrl.text = spannableString
        tvUrl.movementMethod = LinkMovementMethod.getInstance()

        // Handle "Read more" button click if needed
        val btnReadMore = findViewById<AppCompatButton>(R.id.btn_read_more)
        btnReadMore.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
    }
}
