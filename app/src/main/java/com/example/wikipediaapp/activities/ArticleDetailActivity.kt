package com.example.wikipediaapp.activities

import android.graphics.Bitmap
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.wikipediaapp.R
import com.example.wikipediaapp.models.WikiPage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_article_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import android.webkit.WebSettings
import android.os.Build
import android.view.Menu
import com.example.wikipediaapp.WikiApplication
import com.example.wikipediaapp.managers.WikiManager
import org.jetbrains.anko.toast
import java.lang.Exception


class ArticleDetailActivity : AppCompatActivity() {
    private var wikiManager: WikiManager? = null
    private var currentPage: WikiPage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.wikipediaapp.R.layout.activity_article_detail);

        wikiManager = (applicationContext as WikiApplication).wikiManager

        setSupportActionBar(toolbarx)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val wikiPageJson = intent.getStringExtra("page")

        currentPage = Gson().fromJson<WikiPage>(wikiPageJson, WikiPage::class.java)

        supportActionBar!!.title = currentPage?.title

        article_detail_webview?.webViewClient = object: WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return true
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler!!.proceed();
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon);

              //  page_progress_bar!!.visibility= View.VISIBLE

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
              //  page_progress_bar!!.visibility= View.GONE
            }
        }

        Log.v("rxjs",currentPage!!.fullurl)
        article_detail_webview.settings.loadsImagesAutomatically=true
        article_detail_webview.settings.javaScriptEnabled=true
        article_detail_webview.settings.allowContentAccess= true


        article_detail_webview.loadUrl(currentPage!!.fullurl)
        wikiManager?.addHistory(currentPage!!)

        article_detail_webview.settings.setUseWideViewPort(true)
        article_detail_webview.settings.setLoadWithOverviewMode(true)
        article_detail_webview.settings.setDomStorageEnabled(true)

        article_detail_webview.setHorizontalScrollBarEnabled(false)
        article_detail_webview.settings.setAppCacheEnabled(true)
        article_detail_webview.settings.setDatabaseEnabled(true)
        article_detail_webview.setVerticalScrollBarEnabled(false)
        article_detail_webview.settings.setBuiltInZoomControls(true)
        article_detail_webview.settings.setDisplayZoomControls(false)
        article_detail_webview.settings.setAllowFileAccess(true)

        article_detail_webview.setScrollbarFadingEnabled(false)
        article_detail_webview.settings.setCacheMode(WebSettings.LOAD_NO_CACHE)
        article_detail_webview.setWebViewClient(WebViewClient())

        article_detail_webview.setInitialScale(1)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?):Boolean{
        if(item!!.itemId == android.R.id.home){
            finish();
        }
        else if (item!!.itemId== R.id.action_favorite){
            try {
                //first determine if article is already a favorite or not
                if(wikiManager!!.getIsFavorite(currentPage!!.pageid!!)){
                    wikiManager!!.removeFavorite(currentPage!!.pageid!!)
                    toast("Article removed from favorites")
                }else{
                    wikiManager!!.addFavorite(currentPage!!)
                    toast("Article added to favorites")
                }
            }catch (ex: Exception){
                toast("Unable to update this article")
            }

        }
        return true;
    }
}
