package com.mabbr.news.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mabbar.newsapp.R
import com.mabbar.newsapp.databinding.ItemNewsBinding
import com.mabbr.news.ArticlesItem

class News_adapter(var item: List<ArticlesItem>?) :
    RecyclerView.Adapter<News_adapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = item?.get(position)
        if (items != null) {
            holder.bind(items)
        };
        Glide.with(holder.itemView)
            .load(items?.urlToImage)
            .into(holder.itemBinding.image)
    }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
//        return ViewHolder(view)
      val viewBinding: ItemNewsBinding =DataBindingUtil.inflate(LayoutInflater.from(parent.context),
      R.layout.item_news,parent,false)
      return ViewHolder(viewBinding)

    }

    override fun getItemCount(): Int {

        return item?.size ?: 0;
    }

    fun changeData(data: List<ArticlesItem?>?) {
        item = data as List<ArticlesItem>?;
        notifyDataSetChanged()
    }

    class ViewHolder(val itemBinding:ItemNewsBinding) : RecyclerView.ViewHolder(itemBinding.root) {
//        val title: TextView = itemView.findViewById(R.id.title)
//        val outhar: TextView = itemView.findViewById(R.id.author)
//        val date_time: TextView = itemView.findViewById(R.id.date_time)
//        val image: ImageView = itemView.findViewById(R.id.image)
        fun bind(item:ArticlesItem){
            itemBinding.item=item
    itemBinding.invalidateAll()
        }


    }
}