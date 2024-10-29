package com.example.timer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.timer.databinding.ActivityMainBinding
import java.util.Locale
import kotlin.text.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // start_pause 버튼 리스너 설정
        binding.btnStartPause.setOnClickListener {
            // 현재 텍스트가 시작 텍스트일 경우 타이머 시작, 아닐 경우 타이머 일시정지
            if (binding.btnStartPause.text == getString(R.string.btn_start)) {
                binding.btnStartPause.text = getString(R.string.btn_pause)
                startTimer()
            } else {
                binding.btnStartPause.text = getString(R.string.btn_start)
                stopTimer()
            }
        }

        // clear 버튼 리스너 설정
        binding.btnClear.setOnClickListener {
            binding.btnStartPause.text = getString(R.string.btn_start)
            resetTimer()
            binding.tvTime.text = getString(R.string.tv_time)
        }
    }

    // Timer class
    inner class Timer : Thread() {
        var isRunning = true
        private var seconds = 0

        override fun run() {
            while (isRunning) {
                try {
                    sleep(10) // 0.01초 단위
                    seconds++
                    // UI 업데이트 (메인 스레드에서 업데이트 해줘야 함)
                    runOnUiThread {
                        val min = seconds / 6000
                        val sec = seconds / 100 % 60
                        val msec = seconds % 100
                        binding.tvTime.text = String.format(Locale.getDefault(), "%02d:%02d,%02d", min, sec, msec)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

        fun resetTimer() {
            isRunning = false
            seconds = 0
        }
    }

    private fun startTimer() {
        timer = Timer()
        timer?.start()
    }

    private fun stopTimer() {
        timer?.isRunning = false
    }

    private fun resetTimer() {
        timer?.resetTimer()
        timer = null
    }
}
