package com.kennethbchen.smash2152scoutingsystem

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.kennethbchen.smash2152scoutingsystem.lib.ScoutedNumber
import com.kennethbchen.smash2152scoutingsystem.lib.ScoutedString
import com.kennethbchen.smash2152scoutingsystem.lib.ScoutingForm

class DeepSpacePage : AppCompatActivity() {
    private var submitButton: Button? = null
    private var ad: Dialog? = null

    private var deepSpace = ScoutingForm(21)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_space_page)

        var dBuilder = AlertDialog.Builder(this)
        dBuilder.setTitle("Error")
        dBuilder.setMessage("Not all fields filled out!")

        dBuilder.setPositiveButton("Ok"){
                _, _ -> null
        }
        var validationFailAlert = dBuilder.create()



        submitButton = findViewById(resources.getIdentifier("submit", "id",packageName))
        submitButton?.setOnClickListener {
            if(validateAll()){
                println(deepSpace)
            } else {
                validationFailAlert.show()
            }
        }
        getAllElements()

    }

    private fun validateAll(): Boolean{
        var output = true

        for(i in deepSpace.allValues.indices) {
            deepSpace.allValues[i]?.getValue()
            if(deepSpace.allValues[i]?.validateValue() == false){
                output = false
                break
            }
        }
        return output
    }

    private fun getAllElements(){
        //Get everything with an id with a "v" prefix
        for(i in deepSpace.allValues.indices){
            var id = resources.getIdentifier("v$i", "id", packageName)
            var view = findViewById<TextView>(id)

            //The type of view changes the operation done to it
            when(view){
                is Button -> {
                    deepSpace.allValues[i] = ScoutedNumber()
                    var value = deepSpace.allValues[i] as ScoutedNumber

                    view.setOnClickListener {
                        value.incrementValue()
                        view.text = value.getValue()
                    }

                    view.setOnLongClickListener {
                        value.decrementValue()
                        view.text = value.getValue()
                        true
                    }
                }
                is TextView -> {
                    var value =  ScoutedString(view)
                    deepSpace.allValues[i] = value
                }
            }
        }
    }


}
