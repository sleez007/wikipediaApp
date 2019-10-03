package com.example.wikipediaapp.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.wikipediaapp.R
import com.example.wikipediaapp.WikiApplication
import com.example.wikipediaapp.activities.SearchActivity
import com.example.wikipediaapp.activities.SettingsActivity
import com.example.wikipediaapp.adapters.ArticleCardRecyclerAdapter
import com.example.wikipediaapp.managers.WikiManager
import com.example.wikipediaapp.models.WikiResult
import com.example.wikipediaapp.providers.ArticleDataProvider
import kotlinx.android.synthetic.main.fragment_explore.*
import java.lang.Exception


class ExploreFragment : Fragment() {

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.explore_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.action_mode){
          //  val intent: Intent = Intent(context,SettingsActivity::class.java)
           // startActivity(intent)
        }
        return true
    }

   // private val articleProvider: ArticleDataProvider = ArticleDataProvider()
    private var wikiManager: WikiManager? = null
    var searchCardView: CardView? = null
    var exploreRecycler: RecyclerView? = null
    var refresher:SwipeRefreshLayout? = null;
    var adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_explore, container, false)

        searchCardView= view.findViewById<CardView>(R.id.search_card_view)
        searchCardView!!.setOnClickListener {
            val searchIntent = Intent(context,SearchActivity::class.java)
            context?.startActivity(searchIntent)
        }

        refresher = view.findViewById<SwipeRefreshLayout>(R.id.refresher)
        refresher?.setOnRefreshListener {

            getRandomArticle()
        }

        exploreRecycler = view.findViewById<RecyclerView>(R.id.explore_article_recycler)
        exploreRecycler!!.layoutManager= StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        exploreRecycler!!.adapter = adapter
        getRandomArticle()
        return view;
       // getRandomArticle()
    }

    private fun getRandomArticle(){
        refresher?.isRefreshing = true

        try {
            //lamdba argument had to moved out of paranthesis
            wikiManager?.getRandom(15) { wikiResult ->
                adapter.currentResult.clear()
                adapter.currentResult.addAll(wikiResult.query!!.pages)
                activity?.runOnUiThread{adapter.notifyDataSetChanged()
                    refresher?.isRefreshing = false
                }
            }
        }catch (ex:Exception){
            var builder: AlertDialog.Builder = AlertDialog.Builder(activity as Context)
            builder.setMessage(ex.message).setTitle("Oops!")
            val dialog = builder.create()
            dialog.show()
        }

    }

}
