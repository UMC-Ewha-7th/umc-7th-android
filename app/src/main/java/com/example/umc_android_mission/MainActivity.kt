package com.example.umc_android_mission

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_android_mission.databinding.ActivityMainBinding
import com.example.umc_android_mission.ui.CalendarFragment
import com.example.umc_android_mission.ui.EditFragment
import com.example.umc_android_mission.ui.HomeFragment
import com.example.umc_android_mission.ui.MyPageFragment

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_from_left,
                            R.anim.slide_to_right
                        )
                        .replace(R.id.fragment_container, HomeFragment())
                        .commit()
                    true
                }
                R.id.nav_edit -> {
                    if(supportFragmentManager.findFragmentById(R.id.fragment_container) is HomeFragment) {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                R.anim.slide_from_right,
                                R.anim.slide_to_left
                            )
                            .replace(R.id.fragment_container, EditFragment())
                            .commit()
                    } else {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                R.anim.slide_from_left,
                                R.anim.slide_to_right
                            )
                            .replace(R.id.fragment_container, EditFragment())
                            .commit()
                    }
                    true
                }
                R.id.nav_calendar -> {
                    if(supportFragmentManager.findFragmentById(R.id.fragment_container) is MyPageFragment) {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                R.anim.slide_from_left,
                                R.anim.slide_to_right
                            )
                            .replace(R.id.fragment_container, CalendarFragment())
                            .commit()
                    } else {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                R.anim.slide_from_right,
                                R.anim.slide_to_left
                            )
                            .replace(R.id.fragment_container, CalendarFragment())
                            .commit()
                    }
                    true
                }
                R.id.nav_mypage -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_from_right,
                            R.anim.slide_to_left
                        )
                        .replace(R.id.fragment_container, MyPageFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}