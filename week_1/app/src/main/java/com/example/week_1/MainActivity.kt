package com.example.week_1

import com.example.week_1.R

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ImageView 클릭 이벤트 설정
        val imageView = findViewById<ImageView>(R.id.happy) // 원하는 ImageView의 ID 사용
        imageView.setOnClickListener {
            // 클릭 시 SecondActivity로 이동
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }
}
