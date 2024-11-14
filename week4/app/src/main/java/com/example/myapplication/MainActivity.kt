package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var total = 0
    var started = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStart.setOnClickListener {
            start()
        }

        binding.buttonPause.setOnClickListener {
            pause()
        }

        binding.buttonStop.setOnClickListener {
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
                     binding.textTimer.text = formatTime(total)
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
        binding.textTimer.text = formatTime(total)

    }

    fun formatTime(time:Int):String{
        val minute = String.format("%02d", time/60)
        val second = String.format("%02d",time%60)
        return "$minute : $second"

    }
}