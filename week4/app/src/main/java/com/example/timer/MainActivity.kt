package com.example.timer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.timer.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var timerJob: Job? = null
    private var seconds = 0

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
        timerJob = CoroutineScope(Dispatchers.Default).launch { // 백그라운드에서 작업하도록 설정
            while (isActive) { // 타이머 코루틴이 액티브 되어있을 경우 타이머 시간 증가
                delay(10) // 0.01초 단위
                seconds++
                withContext(Dispatchers.Main) { // 메인 스레드에서 UI 업데이트
                    updateTimerUI()
                }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }

    private fun resetTimer() {
        stopTimer()
        seconds = 0
        updateTimerUI()
    }

    private fun updateTimerUI() {
        val min = seconds / 6000
        val sec = seconds / 100 % 60
        val msec = seconds % 100
        binding.tvTime.text = String.format(Locale.getDefault(), "%02d:%02d,%02d", min, sec, msec)
    }
}
