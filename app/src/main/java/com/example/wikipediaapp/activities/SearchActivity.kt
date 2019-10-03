package com.example.wikipediaapp.activities

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wikipediaapp.R
import com.example.wikipediaapp.WikiApplication
import com.example.wikipediaapp.adapters.ArticleListItemRecyclerAdapter
import com.example.wikipediaapp.managers.WikiManager
import com.example.wikipediaapp.providers.ArticleDataProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    private var wikiManager: WikiManager? = null
    // private val articleProvider: ArticleDataProvider = ArticleDataProvider()
    private var adapter : ArticleListItemRecyclerAdapter = ArticleListItemRecyclerAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search);
         wikiManager = (applicationContext as WikiApplication).wikiManager
        setSupportActionBar(toolbar);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        search_result_recycler.layoutManager = LinearLayoutManager(this)
        search_result_recycler.adapter =adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId== android.R.id.home){
            finish();
        }
        return true;
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu);
        val searchItem = menu!!.findItem(R.id.action_search);
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager;
       // val searchView = searchItem!!.actionView as SearchView;
        (menu.findItem(R.id.action_search).actionView as  SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            this.setIconifiedByDefault(false)
            this.requestFocus();
            this.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    wikiManager?.search(query.toString(),0,20,{wikiResult ->
                        adapter.currentResults.clear()
                        adapter.currentResults.addAll(wikiResult.query!!.pages)
                        runOnUiThread{adapter.notifyDataSetChanged()}
                    })
                    println("Updated search");
                    return false;
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false;
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }
}
