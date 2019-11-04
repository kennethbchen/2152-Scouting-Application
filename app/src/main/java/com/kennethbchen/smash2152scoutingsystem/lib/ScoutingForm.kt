package com.kennethbchen.smash2152scoutingsystem.lib

class ScoutingForm(var numOfValues: Int) {

    var allValues = Array<ScoutedValue?>(numOfValues){null}

    override fun toString(): String {
        var output = ""

        for(i in allValues.indices){

            //Filter out all commas because they screw up CSVs
            var regex = ",".toRegex()
            output += regex.replace(allValues[i]?.getValue() as CharSequence, "")

            if(i < allValues.size - 1){
                output += ", "
            }
        }
        return output
    }

}


