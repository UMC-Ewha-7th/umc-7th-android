package com.example.umc_android_mission

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_android_mission.databinding.ActivitySelectStampBinding

class SelectStampActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySelectStampBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectStampBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, NextActivity::class.java)
        binding.yellowStamp.setOnClickListener { startActivity(intent) }
    }
}