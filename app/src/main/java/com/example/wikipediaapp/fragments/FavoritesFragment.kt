package com.example.wikipediaapp.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.wikipediaapp.R
import com.example.wikipediaapp.WikiApplication
import com.example.wikipediaapp.adapters.ArticleCardRecyclerAdapter
import com.example.wikipediaapp.adapters.ArticleListItemRecyclerAdapter
import com.example.wikipediaapp.managers.WikiManager
import com.example.wikipediaapp.models.WikiPage
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.jetbrains.anko.doAsync


class FavoritesFragment : Fragment() {
    private var wikiManager: WikiManager? = null
    var favoritesRecycler: RecyclerView? = null
    private val adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        favoritesRecycler = view.findViewById<RecyclerView>(R.id.favorites_article_recycler)
        favoritesRecycler!!.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        favoritesRecycler!!.adapter=adapter
        return view
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val favoriteArticles = wikiManager!!.getFavorites()
            adapter.currentResult.clear()
            adapter.currentResult.addAll(favoriteArticles as ArrayList<WikiPage>)
            activity?.runOnUiThread { adapter.notifyDataSetChanged() }
        }
    }


}
