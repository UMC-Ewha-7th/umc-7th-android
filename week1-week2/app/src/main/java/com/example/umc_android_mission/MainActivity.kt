package com.example.umc_android_mission

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.umc_android_mission.databinding.ActivityMainBinding
import com.example.umc_android_mission.ui.CalendarFragment
import com.example.umc_android_mission.ui.EditFragment
import com.example.umc_android_mission.ui.HomeFragment
import com.example.umc_android_mission.ui.MyPageFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    ReplaceFragment(HomeFragment(), R.anim.slide_from_right, R.anim.slide_to_left)
                    true
                }

                R.id.nav_edit -> {
                    if (supportFragmentManager.findFragmentById(R.id.fragment_container) is HomeFragment) {
                        ReplaceFragment(EditFragment(), R.anim.slide_from_right, R.anim.slide_to_left)
                    } else {
                        ReplaceFragment(EditFragment(), R.anim.slide_from_left, R.anim.slide_to_right)
                    }
                    true
                }

                R.id.nav_calendar -> {
                    if (supportFragmentManager.findFragmentById(R.id.fragment_container) is MyPageFragment) {
                        ReplaceFragment(CalendarFragment(), R.anim.slide_from_left, R.anim.slide_to_right)
                    } else {
                        ReplaceFragment(CalendarFragment(), R.anim.slide_from_right, R.anim.slide_to_left)
                    }
                    true
                }

                R.id.nav_mypage -> {
                    ReplaceFragment(MyPageFragment(), R.anim.slide_from_right, R.anim.slide_to_left)
                    true
                }

                else -> false
            }
        }
    }

    fun ReplaceFragment(fragment: Fragment, exitAnim: Int, enterAnim: Int) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                exitAnim,
                enterAnim
            )
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}