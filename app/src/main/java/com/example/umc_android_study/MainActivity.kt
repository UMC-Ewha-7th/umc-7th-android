package com.example.umc_android_study

import ViewPagerAdapter
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // activity_main.xml 파일 설정

        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)

        // ViewPager2와 어댑터 연결
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // TabLayout과 ViewPager2 연동
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Home"
                1 -> tab.text = "Stamp"
                2 -> tab.text = "My Page"
            }
        }.attach()

        // BottomNavigationView와 ViewPager2 연동
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_home -> viewPager.currentItem = 0
                R.id.navigation_stamp -> viewPager.currentItem = 1
                R.id.navigation_mypage -> viewPager.currentItem = 2
            }
            true
        }

        // ViewPager2 페이지 전환 시 BottomNavigationView 업데이트
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> bottomNavigationView.selectedItemId = R.id.navigation_home
                    1 -> bottomNavigationView.selectedItemId = R.id.navigation_stamp
                    2 -> bottomNavigationView.selectedItemId = R.id.navigation_mypage
                }
            }
        })
    }
}
