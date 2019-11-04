package com.kennethbchen.smash2152scoutingsystem.lib

class ScoutedNumber: ScoutedValue(){

    var count: Int = 0

    override fun validateValue(): Boolean {
        return true
    }

    override fun getValue(): String{
        return count.toString()
    }

    fun incrementValue(){
        count++
    }

    fun decrementValue(){
        count--
        if(count < 0) count = 0
    }
}