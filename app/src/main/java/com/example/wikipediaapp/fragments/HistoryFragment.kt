package com.example.wikipediaapp.fragments


import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.wikipediaapp.R
import com.example.wikipediaapp.WikiApplication
import com.example.wikipediaapp.adapters.ArticleCardRecyclerAdapter
import com.example.wikipediaapp.adapters.ArticleListItemRecyclerAdapter
import com.example.wikipediaapp.managers.WikiManager
import com.example.wikipediaapp.models.WikiPage
import kotlinx.android.synthetic.main.fragment_history.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class HistoryFragment : Fragment() {

    init {
        setHasOptionsMenu(true)
    }
    private val adapter: ArticleListItemRecyclerAdapter = ArticleListItemRecyclerAdapter()
    private var wikiManager: WikiManager? = null
    var historyRecycler: RecyclerView? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        historyRecycler = view.findViewById<RecyclerView>(R.id.history_article_recycler)
        historyRecycler!!.layoutManager= LinearLayoutManager(context)
        historyRecycler!!.adapter=adapter
        return view
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val favoriteArticles = wikiManager!!.getHistory()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(favoriteArticles as ArrayList<WikiPage>)
            activity?.runOnUiThread { adapter.notifyDataSetChanged() }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.history_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.action_clear_history){
            // show confirmation history
            activity?.alert("are you sure you want to clear history?","Confirm"){
                yesButton {
                    //yes was hit...
                    // clear history async
                    adapter.currentResults.clear()
                    doAsync {
                        wikiManager?.clearHistory()
                    }
                    activity?.runOnUiThread { adapter.notifyDataSetChanged() }
                }

                noButton {
                    //do something here if you want, but we dont need it
                }
            }?.show()
        }

        return true
    }


}
