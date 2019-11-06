package com.kennethbchen.smash2152scoutingsystem.lib

import android.widget.Button

class ScoutedNumber(val button: Button): ScoutedValue(){

    var count: Int = 0

    override fun validateValue(): Boolean {
        return true
    }

    override fun getValue(): String{
        return count.toString()
    }

    override fun reset(){
        count = 0
        button.text = count.toString()
    }
    fun incrementValue(){
        count++
    }

    fun decrementValue(){
        count--
        if(count < 0) count = 0
    }


}