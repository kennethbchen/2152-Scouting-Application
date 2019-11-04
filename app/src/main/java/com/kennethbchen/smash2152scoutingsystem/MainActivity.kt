package com.kennethbchen.smash2152scoutingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun loadScoutingPage(view: View){
        val intent = Intent(this, DeepSpacePage::class.java).apply {  }
        startActivity(intent)
    }
}
