package com.example.uselessmachine

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar_main_look_busy.setVisibility(View.INVISIBLE)
        text_main_filesLoaded.setVisibility(View.INVISIBLE)
        text_main_filesLoaded.setBackgroundColor(Color.argb(
            100,
            54, 255, 235
        ))
        text_main_filesLoaded.setText("")
        progressBar_main_look_busy.setProgress(0)
        // the one function that View.OnClickListener has is onClick(v: View!)
        // this lambda below is implementing that one function onClick without really mentioning it
        // explicity. the one parameter is referenced by "it". So to access that view, I can use "it"
        //when theres one parameter in the funtion, "it" is used to refer to that parameter
        //${} is a string template

        button_main_look_busy.setOnClickListener {
            button_main_self_destruct.setVisibility(View.INVISIBLE)
            button_main_look_busy.setVisibility(View.INVISIBLE)
            switch_main_useless.setVisibility(View.INVISIBLE)
            progressBar_main_look_busy.setVisibility(View.VISIBLE)
            text_main_filesLoaded.setVisibility(View.VISIBLE)
            object : CountDownTimer(10000,500) {
                override fun onFinish() {
                    progressBar_main_look_busy.setProgress(0)
                    progressBar_main_look_busy.setVisibility(View.INVISIBLE)
                    text_main_filesLoaded.setVisibility(View.INVISIBLE)
                    button_main_self_destruct.setVisibility(View.VISIBLE)
                    button_main_look_busy.setVisibility(View.VISIBLE)
                    switch_main_useless.setVisibility(View.VISIBLE)
                }

                override fun onTick(p0: Long) {
                    progressBar_main_look_busy.incrementProgressBy(5)
                    text_main_filesLoaded.setText("${100 - p0/100} Files Loaded")
                }

            }.start()
            //Toast.makeText(this, "Hello, this is the text on the button ${(it as Button).text.toString()}", Toast.LENGTH_SHORT).show()
        }
        
        //to listen to a switch, you can use the OnCheckChangedListener
        switch_main_useless.setOnCheckedChangeListener { compoundButton, isChecked ->
            // 1. toast the status of the button (check, or not checked)

            Toast.makeText(this, "The button is: " + isChecked.toString(), Toast.LENGTH_SHORT).show()
            //2. if the butoon is checked, uncheck it
            if (isChecked == true){
                //ideally wait a bit of time
                //but Thread.sleep is illegal on the main ui thread
                // janky app -- doesnt respond to your clicks immediately
                // CountDownTimer is effectively making a seperate thread to keep track of the time
                // we are going to make an anonymous inner class using CountDownTimer
                // we are not naming the class... we are just implementing the methods
                // its a oen time thing used right here
                // making anonymous inner class saying this object extends CountDownTimer
                val upperLimit = 5000
                val lowerLimit = 1000
                val uncheckTimer = object : CountDownTimer((lowerLimit..upperLimit).random().toLong(),1000) {
                    override fun onFinish() {

                        switch_main_useless.isChecked=false
                    }

                    override fun onTick(p0: Long) {
                        if (!switch_main_useless.isChecked){
                            onFinish()
                        }
                    }

                }
                uncheckTimer.start()
            }

        }

        // look up countdowntiimer api and see what is needed to implement your own custom timer
        // how do you start it? how do you stop it? how do you do things when its done
        button_main_self_destruct.setOnClickListener {
            button_main_self_destruct.isEnabled = false
            object : CountDownTimer(10000, 250) {
                var isRed = false
                override fun onTick(millisUntilFinished: Long) {
                    var timeLeftInQuarterSeconds = millisUntilFinished/250
                    if (timeLeftInQuarterSeconds > 20){
                        if ((timeLeftInQuarterSeconds%4).toInt() == 0 && !isRed){
                            layout_main.setBackgroundColor(
                                Color.rgb(
                                    255,
                                    0,
                                    0
                                )
                            )
                            isRed = true
                        }
                        else{
                            layout_main.setBackgroundColor(
                                Color.rgb(
                                    255,
                                    255,
                                    255
                                )
                            )
                            isRed = false
                        }
                    }
                    else{
                        if ((timeLeftInQuarterSeconds%2).toInt() == 0 && !isRed){
                            layout_main.setBackgroundColor(
                                Color.rgb(
                                    255,
                                    0,
                                    0
                                )
                            )
                            isRed = true
                        }
                        else{
                            layout_main.setBackgroundColor(
                                Color.rgb(
                                    255,
                                    255,
                                    255
                                )
                            )
                            isRed = false
                        }
                    }
                    button_main_self_destruct.setText("Core Meltdown in: " + millisUntilFinished / 1000)
                }

                override fun onFinish() {
                    button_main_self_destruct.setText("Boom!")
                    finish()
                }
            }.start()
        }
    }
}