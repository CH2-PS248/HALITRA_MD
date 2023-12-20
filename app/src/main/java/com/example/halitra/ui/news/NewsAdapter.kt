package com.example.halitra.ui.news

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.halitra.R
import com.example.halitra.data.model.ArticlesItem

import com.example.halitra.ui.detail.DetailActivity

class NewsAdapter (val dataNews: List<ArticlesItem?>?) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    class MyViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val imgNews = view.findViewById<ImageView>(R.id.image)
        val titleNews = view.findViewById<TextView>(R.id.tvTitle)
        val urlNews = view.findViewById<TextView>(R.id.tvUrl)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleNews.text = dataNews?.get(position)?.title
        holder.urlNews.text = dataNews?.get(position)?.url

        Glide.with(holder.imgNews)
            .load(dataNews?.get(position)?.urlToImage)
            .error(R.drawable.iconhalitra)
            .into(holder.imgNews)

        holder.itemView.setOnClickListener {
            val title = dataNews?.get(position)?.title
            Toast.makeText(holder.itemView.context, "${title}", Toast.LENGTH_SHORT).show()



            holder.itemView.setOnClickListener {
                val title = dataNews?.get(position)?.title
                val imageUrl = dataNews?.get(position)?.urlToImage
                val url = dataNews?.get(position)?.url
                val description = dataNews?.get(position)?.description
                val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
                    putExtra("title", title)
                    putExtra("imageUrl", imageUrl)
                    putExtra("url",url)
                    putExtra("description", description)

                }
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        if (dataNews != null){
            return dataNews.size
        }
        return 0
    }
}