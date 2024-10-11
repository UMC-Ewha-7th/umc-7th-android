package com.example.umc_android_study

import com.example.umc_android_study.R
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // activity_main.xml 파일을 설정

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)


        // 첫 화면에 보여질 Fragment 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_home -> selectedFragment = HomeFragment()
                R.id.navigation_stamp -> selectedFragment = StampFragment()
                R.id.navigation_mypage -> selectedFragment = MyPageFragment()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit()
            }
            true
        }

        // Edge-to-Edge 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 각 ImageView를 찾아서 클릭 이벤트 설정
        val imageView = findViewById<ImageView>(R.id.imageView)
        val imageView2 = findViewById<ImageView>(R.id.imageView2)
        val imageView3 = findViewById<ImageView>(R.id.imageView3)
        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        val imageView5 = findViewById<ImageView>(R.id.imageView5)

        // ImageView 1 클릭 이벤트
        imageView.setOnClickListener {
            val intent = Intent(
                this@MainActivity,
                nextpage::class.java // 다음 페이지로 이동하는 액티비티 설정
            )
            startActivity(intent)
        }

        // ImageView 2 클릭 이벤트
        imageView2.setOnClickListener {
            val intent = Intent(
                this@MainActivity,
                nextpage::class.java
            )
            startActivity(intent)
        }

        // ImageView 3 클릭 이벤트
        imageView3.setOnClickListener {
            val intent = Intent(
                this@MainActivity,
                nextpage::class.java
            )
            startActivity(intent)
        }

        // ImageView 4 클릭 이벤트
        imageView4.setOnClickListener {
            val intent = Intent(
                this@MainActivity,
                nextpage::class.java
            )
            startActivity(intent)
        }

        // ImageView 5 클릭 이벤트
        imageView5.setOnClickListener {
            val intent = Intent(
                this@MainActivity,
                nextpage::class.java
            )
            startActivity(intent)
        }
    }
}
