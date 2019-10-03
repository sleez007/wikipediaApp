package com.example.wikipediaapp.providers

import android.util.Log
import com.example.wikipediaapp.models.Urls
import com.example.wikipediaapp.models.WikiResult
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import java.io.Reader
import java.lang.Exception


class ArticleDataProvider {

    init {
        FuelManager.instance.baseHeaders = mapOf("User-Agent" to "Pluralsight Wikipedia")
    }

    fun search(term:String, skip: Int, take: Int, responseHandler:(result: WikiResult)->Unit?){

        Urls.getSearchUrl(term, skip, take).httpGet().responseObject(WikipediaDataDeserializer()){ request, response, result
            ->
            if(response.statusCode != 200){
                throw Exception("Unable to get articles")
            }

            val (data, _) = result
            responseHandler.invoke(data as WikiResult)
            //do something with result
        }
    }

    fun getRandom(take: Int, responseHandler: (result: WikiResult)->Unit?){
        Urls.getRandomUrl(take).httpGet().responseObject(WikipediaDataDeserializer()){
                request, response, result->
            val (data, _) = result
                Log.i("mm",data.toString())
            if(response.statusCode != 200){
                throw Exception("Unable to get articles ${response.statusCode}")
            }

            responseHandler.invoke(data as WikiResult)

        }
    }

    class WikipediaDataDeserializer: ResponseDeserializable<WikiResult>{
        override fun deserialize(reader: Reader)= Gson().fromJson(reader,WikiResult::class.java)

    }
}

