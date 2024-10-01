package com.example.umc_android_mission

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_android_mission.databinding.ActivityNextBinding

class NextActivity: AppCompatActivity() {
    private lateinit var binding: ActivityNextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNextBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        setContentView(binding.root)
    }
}