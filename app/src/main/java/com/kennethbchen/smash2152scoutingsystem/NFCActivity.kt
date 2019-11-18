package com.kennethbchen.smash2152scoutingsystem

import android.app.PendingIntent
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


class NFCActivity : AppCompatActivity() {

    var rootDir: File? = null
    var stagedDir: File? = null
    var backupDir: File? = null

    var nfcAdapter: NfcAdapter? = null
    private var nfcPendingIntent: PendingIntent? = null
    var readTagFilters: Array<IntentFilter>? = null

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
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        checkNFCEnabled()


        nfcPendingIntent = PendingIntent.getActivity(this,0, Intent(this, this::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)

        var ndefDetected = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)

        readTagFilters = Array<IntentFilter>(1){ndefDetected}

    }

    override fun onResume() {
        super.onResume()
        checkNFCEnabled()

        //Log.d("DEBUG", "onResume: $intent")

        if (intent.action != null) {
            if (intent.action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
                Log.d("Debug", "NDEF FOUND")
            }
            nfcAdapter?.enableForegroundDispatch(this, nfcPendingIntent, readTagFilters, null)

        }
    }
    override fun onPause() {
        super.onPause()
        //Log.d("DEBUG", "onPause: $intent")
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent?.action) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
            }
        }
    }

    fun getNdefMessageFromIntent(intent: Intent): Array<NdefMessage?>{
        var msgs = arrayOfNulls<NdefMessage>(0)
        var action = intent.action

        if(action.equals(NfcAdapter.ACTION_TAG_DISCOVERED) || action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)){
            var rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            if(rawMsgs != null){
                msgs = arrayOfNulls(rawMsgs.size)
                for(i in rawMsgs.indices){
                    msgs[i] = rawMsgs[i] as NdefMessage
                }
            } else {
                var empty = byteArrayOf()
                var record = NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty)
                var msg = NdefMessage(record)
                msgs = Array<NdefMessage?>(1) {msg}
            }
        } else {
            Log.e("DEBUG", "Unknown Intent.")
            finish()
        }
        return msgs
    }

    fun checkNFCEnabled(){
        if(nfcAdapter?.isEnabled == false){
            Toast.makeText(this, "NFC is not supported on this device", Toast.LENGTH_LONG).show()
        }
    }
}
