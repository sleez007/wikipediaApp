package com.example.wikipediaapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.wikipediaapp.R
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.toast

class SettingsActivity : AppCompatActivity() {

    companion object{
        var applyThemex: Int = R.style.DarkTheme_noActionBar
    }

    override fun onCreate(savedInstanceState: Bundle?) {

            //toast("dont dark")
        setTheme(applyThemex)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if(applyThemex==R.style.DarkTheme_noActionBar){
            mode_switch.isChecked= true
        }
        
        mode_switch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                applyThemex=R.style.DarkTheme_noActionBar
                startActivity(getIntent());
                finish();
            }else{
                applyThemex=R.style.AppTheme_noActionBar
                startActivity(getIntent());
                finish();
            }
        }


    }

}
