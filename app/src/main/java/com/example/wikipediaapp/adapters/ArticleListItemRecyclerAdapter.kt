package com.example.wikipediaapp.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.wikipediaapp.R
import com.example.wikipediaapp.holders.CardHolder
import com.example.wikipediaapp.holders.ListItemHolder
import com.example.wikipediaapp.models.WikiPage

class ArticleListItemRecyclerAdapter : RecyclerView.Adapter<ListItemHolder>() {

    val currentResults: ArrayList<WikiPage> = ArrayList<WikiPage>()

    override fun getItemCount(): Int {
        return currentResults.size
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
       val page =currentResults[position]
        holder.updateWithPage(page)

    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): ListItemHolder {
        var cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_list_item,parent,false)
        return ListItemHolder(cardItem)
    }
}