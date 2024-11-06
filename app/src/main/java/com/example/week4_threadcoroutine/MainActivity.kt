package com.example.week4_threadcoroutine

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.example.week4_threadcoroutine.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var started = false

    // 상수 선언
    private val SET_TIME = 51
    private val RESET = 52

    // 핸들러 선언
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                SET_TIME -> {
                    binding.textTimer.text = formatTime(msg.arg1)
                }
                RESET -> {
                    binding.textTimer.text = "00 : 00"
                }
            }
        }
    }

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

    private fun start() {
        started = true
        thread(start = true) {
            var total = 0
            while (started) {
                Thread.sleep(1000)
                total += 1
                val msg = Message()
                msg.what = SET_TIME
                msg.arg1 = total
                handler.sendMessage(msg)
            }
        }
    }

    private fun pause() {
        started = false
    }

    private fun stop() {
        started = false
        handler.sendEmptyMessage(RESET)
    }

    private fun formatTime(time: Int): String {
        val minute = String.format("%02d", time / 60)
        val second = String.format("%02d", time % 60)
        return "$minute : $second"
    }
}
