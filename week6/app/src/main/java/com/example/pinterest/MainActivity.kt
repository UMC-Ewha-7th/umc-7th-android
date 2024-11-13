package com.example.pinterest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pinterest.databinding.ActivityMainBinding
import com.example.pinterest.ui.AddFragment
import com.example.pinterest.ui.HomeFragment
import com.example.pinterest.ui.MessageFragment
import com.example.pinterest.ui.ProfileFragment
import com.example.pinterest.ui.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNav()
    }

    private fun initBottomNav() {
        supportFragmentManager.beginTransaction()
           .replace(R.id.main_fragment_container, HomeFragment())
           .commitAllowingStateLoss()

        binding.mainBottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, HomeFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, SearchFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.add -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, AddFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.message -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, MessageFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, ProfileFragment())
                        .commitAllowingStateLoss()
                    true
                }
                else -> false
            }
        }

    }
}