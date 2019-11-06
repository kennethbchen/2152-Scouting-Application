package com.kennethbchen.smash2152scoutingsystem.lib

import android.widget.TextView

open class ScoutedString(var view: TextView): ScoutedValue() {
    var stringVal: String = ""

    override fun validateValue(): Boolean {
        return stringVal.isNotEmpty()
    }

    override fun getValue(): String{
        stringVal = view.text.toString()
        return stringVal
    }

    override fun reset(){
        stringVal = ""
        view.text = stringVal
    }

    override fun toString(): String{
        return stringVal
    }

}