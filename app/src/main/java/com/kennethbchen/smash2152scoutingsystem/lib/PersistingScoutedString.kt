package com.kennethbchen.smash2152scoutingsystem.lib

import android.widget.TextView

class PersistingScoutedString(var textBox: TextView): ScoutedString(textBox) {
    override fun reset() { }
}