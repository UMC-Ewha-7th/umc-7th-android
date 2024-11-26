package com.example.flo_clone.locker.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SavedSongFragment()
            1 -> MusicFileFragment()
            2 -> SavedAlbumFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}