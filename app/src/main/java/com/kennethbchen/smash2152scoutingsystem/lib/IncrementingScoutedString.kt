package com.kennethbchen.smash2152scoutingsystem.lib

import android.widget.TextView

class IncrementingScoutedString(var textBox: TextView): ScoutedString(textBox) {
    override fun reset(){
        var num = Integer.valueOf(textBox.text.toString())
        textBox.text = (num + 1).toString()
    }
}