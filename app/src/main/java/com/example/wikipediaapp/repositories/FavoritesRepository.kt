package com.example.wikipediaapp.repositories

import com.example.wikipediaapp.models.WikiPage
import com.example.wikipediaapp.models.WikiThumbnail
import com.google.gson.Gson
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select

class FavoritesRepository(val databaseHelper:ArticleDatabaseOpenHelper) {
    private val TABLE_NAME: String = "Favorites"

    fun addFavorite(page: WikiPage){
        databaseHelper.use {
            insert(TABLE_NAME,
                "id" to page.pageid,
                "title" to page.title,
                "url" to page.fullurl,
                "thumbnailJson" to Gson().toJson(page.thumbnail)
            )
        }
    }

    fun removeFavoritesById(pageId: Int){
        databaseHelper.use {
            delete(TABLE_NAME,"id={pageId}", "pageId" to pageId)
        }
    }

    fun isArticleFavorite(pageId: Int): Boolean {
        // get Fvaorites and filter
        var pages = getAllFavorites()
        return pages.any {
        page ->
        page.pageid ==pageId
        }
    }

    fun getAllFavorites():ArrayList<WikiPage>{
        var pages = ArrayList<WikiPage>();
        val articleRowParse = rowParser {id:Int,title:String,url:String, thumbnailJson: String ->
            val page = WikiPage()
            page.title = title
            page.pageid = id
            page.fullurl = url
            page.thumbnail = Gson().fromJson(thumbnailJson,WikiThumbnail::class.java)

            pages.add(page)
        }
        databaseHelper.use {
            select(TABLE_NAME).parseList(articleRowParse)
        }
        return pages
    }


}