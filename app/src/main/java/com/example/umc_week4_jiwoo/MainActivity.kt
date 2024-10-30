package com.example.umc_week4_jiwoo

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnClear: Button

    private var isRunning = false
    private var milliseconds = 0
    private var seconds = 0
    private var minutes = 0

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // XML 파일의 뷰 ID 연결
        tvTimer = findViewById(R.id.tvTimer)
        btnStart = findViewById(R.id.btnStart)
        btnClear = findViewById(R.id.btnClear)

        btnStart.setOnClickListener { toggleTimer() }
        btnClear.setOnClickListener { resetTimer() }
    }

    private fun toggleTimer() {
        if (isRunning) {
            stopTimer()
            btnStart.text = "Start"
        } else {
            startTimer()
            btnStart.text = "Stop"
        }
    }

    private fun startTimer() {
        isRunning = true
        handler.post(timerRunnable)
    }

    private fun stopTimer() {
        isRunning = false
        handler.removeCallbacks(timerRunnable)
    }

    private fun resetTimer() {
        stopTimer()
        milliseconds = 0
        seconds = 0
        minutes = 0
        tvTimer.text = "00:00:00"
        btnStart.text = "Start"
    }

    // 10ms 단위로 타이머 갱신
    private val timerRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                milliseconds += 10
                if (milliseconds >= 100) {
                    milliseconds = 0
                    seconds++
                }
                if (seconds >= 60) {
                    seconds = 0
                    minutes++
                }

                tvTimer.text = formatTime(minutes, seconds, milliseconds)
                handler.postDelayed(this, 10)
            }
        }
    }

    // 분:초:밀리초 형식으로 시간 포맷팅
    private fun formatTime(minutes: Int, seconds: Int, milliseconds: Int): String {
        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer() // 액티비티가 종료될 때 타이머 정지
    }
}
