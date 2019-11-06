package com.kennethbchen.smash2152scoutingsystem.lib

abstract class ScoutedValue{
    abstract fun validateValue(): Boolean
    abstract fun getValue(): String
    abstract fun reset()
}