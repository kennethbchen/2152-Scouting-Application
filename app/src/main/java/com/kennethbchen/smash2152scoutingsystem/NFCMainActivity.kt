package com.kennethbchen.smash2152scoutingsystem

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kennethbchen.smash2152scoutingsystem.lib.ResetBehavior
import java.io.File
import android.content.IntentFilter.MalformedMimeTypeException
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.nfc.NdefRecord
import android.os.Debug
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import org.w3c.dom.Text
import java.nio.charset.Charset


class NFCMainActivity : AppCompatActivity() {

    private var rootDir: File? = null
    private var stagedDir: File? = null
    private var backupDir: File? = null

    private var remainingText: TextView? = null

    private var nfcAdapter: NfcAdapter? = null
    private var nfcPendingIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)

        rootDir = this.filesDir
        stagedDir = File(rootDir, "Staged")
        if (stagedDir?.exists() == false) {
            stagedDir?.mkdir()
        }

        backupDir = File(rootDir, "Backup")
        if (backupDir?.exists() == false) {
            backupDir?.mkdir()
        }

        remainingText = findViewById(resources.getIdentifier("formsRemaining", "id", packageName))
        setRemaining()

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        checkNFCEnabled()


        nfcPendingIntent = PendingIntent.getActivity(this,0, Intent(this, this::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
    }

    fun setRemaining(){
        Log.d("Debug", stagedDir?.listFiles()?.size.toString())
        remainingText?.text = stagedDir?.listFiles()?.size.toString() + " Forms Remaining"
    }


    override fun onResume() {
        super.onResume()
        checkNFCEnabled()
        nfcAdapter?.enableForegroundDispatch(this, nfcPendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("Debug", "New Intent Found")
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent?.action) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
                Toast.makeText(this, messages[0].records[0].payload.toString(Charset.defaultCharset()), Toast.LENGTH_LONG).show()

            }
        }

    }


    fun checkNFCEnabled(){
        if(nfcAdapter?.isEnabled == false){
            Toast.makeText(this, "NFC is not supported on this device", Toast.LENGTH_LONG).show()
        }
    }
}
