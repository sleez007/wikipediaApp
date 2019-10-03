package com.example.wikipediaapp

import android.app.Application
import com.example.wikipediaapp.managers.WikiManager
import com.example.wikipediaapp.providers.ArticleDataProvider
import com.example.wikipediaapp.repositories.ArticleDatabaseOpenHelper
import com.example.wikipediaapp.repositories.FavoritesRepository
import com.example.wikipediaapp.repositories.HistoryRepository

class WikiApplication: Application() {
    private var dbHelper : ArticleDatabaseOpenHelper? = null
    private var favoritesRepository: FavoritesRepository? = null
    private var historyRepository: HistoryRepository? = null
    private var wikiProvider : ArticleDataProvider? = null
    var wikiManager:WikiManager? = null
        private set

    override fun onCreate() {
        super.onCreate()
        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        favoritesRepository = FavoritesRepository(dbHelper!!)
        historyRepository = HistoryRepository(dbHelper!!)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider!!,favoritesRepository!!,historyRepository!!)

    }
}