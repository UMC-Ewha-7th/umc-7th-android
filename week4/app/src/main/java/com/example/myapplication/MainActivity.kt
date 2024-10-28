package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var textTimer: TextView
    private lateinit var buttonStart: Button
    private lateinit var buttonPause: Button
    private lateinit var buttonStop: Button

    var total = 0
    var started = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textTimer = findViewById(R.id.textTimer)
        buttonStart = findViewById(R.id.buttonStart)
        buttonPause = findViewById(R.id.buttonPause)
        buttonStop = findViewById(R.id.buttonStop)

        buttonStart.setOnClickListener {
            start()
        }

        buttonPause.setOnClickListener {
            pause()
        }

        buttonStop.setOnClickListener {
           stop()
        }


    }


    fun start(){
         started = true
         val thread = Thread{
             while(true){
                 Thread.sleep(1000)
                 if(!started) break
                 total = total + 1
                 runOnUiThread {
                     textTimer.text = formatTime(total)
                 }

             }

         }
        thread.start()
    }

    fun pause(){
        started = false

    }

    fun stop(){
        started = false
        total =0
        textTimer.text = "00 : 00"

    }

    fun formatTime(time:Int):String{
        val minute = String.format("%02d", time/60)
        val second = String.format("%02d",time%60)
        return "$minute : $second"

    }
}