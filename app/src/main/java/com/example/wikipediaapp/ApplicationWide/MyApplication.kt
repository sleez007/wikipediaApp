package com.example.wikipediaapp.ApplicationWide

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MyApplication:Application(){

    companion object{
        fun setTheme() {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    override fun onCreate() {

        super.onCreate()
        MyApplication.setTheme();
    }
}