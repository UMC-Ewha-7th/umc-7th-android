package com.example.timer

import android.os.Bundle
import android.os.SystemClock
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.timer.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var timerJob: Job? = null
    private var startTime = 0L

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

    private fun startTimer() {
        startTime = SystemClock.elapsedRealtime() // 시작 시간 기록
        timerJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                val elapsed = SystemClock.elapsedRealtime() - startTime // 경과 시간 계산
                updateTimerUI(elapsed)
                delay(10) // UI 업데이트 간격
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }

    private fun resetTimer() {
        stopTimer()
        startTime = 0L
        updateTimerUI(startTime)
    }

    private fun updateTimerUI(elapsedMillis: Long) {
        val min = (elapsedMillis / 60000).toInt()
        val sec = ((elapsedMillis / 1000) % 60).toInt()
        val msec = ((elapsedMillis % 1000) / 10).toInt()
        binding.tvTime.text = String.format(Locale.getDefault(), "%02d:%02d,%02d", min, sec, msec)
    }
}
