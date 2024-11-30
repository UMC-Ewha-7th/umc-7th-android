package com.example.memoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.memoapplication.databinding.ActivityMainBinding
import com.example.memoapplication.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textSecond.text = intent.getStringExtra("data")
    }
}