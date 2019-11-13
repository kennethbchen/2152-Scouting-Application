package com.kennethbchen.smash2152scoutingsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kennethbchen.smash2152scoutingsystem.lib.ResetBehavior
import java.io.File

class NFCActivity : AppCompatActivity() {

    var rootDir: File? = null
    var stagedDir: File? = null
    var backupDir: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)

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
}
