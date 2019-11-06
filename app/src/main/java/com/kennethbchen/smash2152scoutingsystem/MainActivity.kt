package com.kennethbchen.smash2152scoutingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import java.io.File

class MainActivity : AppCompatActivity() {


    var rootDir: File? = null
    var stagedDir: File? = null
    var backupDir: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootDir = this.filesDir
        stagedDir = File(rootDir, "Staged")
        if(stagedDir?.exists() == false){
            stagedDir?.mkdir()
        }

        backupDir = File(rootDir, "Backup")
        if(backupDir?.exists() == false){
            backupDir?.mkdir()
        }
    }

    fun loadScoutingPage(view: View){
        val intent = Intent(this, DeepSpacePage::class.java).apply {  }
        startActivity(intent)
    }

    fun loadNFCPage(view: View){
        val intent = Intent(this, NFCActivity::class.java).apply {}
        startActivity(intent)
    }
}
