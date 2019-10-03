package com.example.wikipediaapp.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.wikipediaapp.R
import com.example.wikipediaapp.holders.CardHolder
import com.example.wikipediaapp.models.WikiPage

class ArticleCardRecyclerAdapter(): RecyclerView.Adapter<CardHolder>() {

    val currentResult: ArrayList<WikiPage> = ArrayList<WikiPage>()

    override fun getItemCount(): Int {
        return currentResult.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        var page = currentResult[position]
        holder.updateWithPage(page)
    }
//$(PRODUCT_BUNDLE_IDENTIFIER)
    //org.reactjs.native.example.$(PRODUCT_NAME:rfc1034identifier)
    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): CardHolder {
        var cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_card_item,parent,false)
        return CardHolder(cardItem)
    }
}