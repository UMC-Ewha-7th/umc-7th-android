package com.example.flo_clone.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo_clone.MainActivity
import com.example.flo_clone.R
import com.example.flo_clone.databinding.FragmentHomeBinding
import com.example.flo_clone.ui.album.AlbumFragment

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeAlbumImgIv1.setOnClickListener {
            var albumFragment = AlbumFragment()
            albumFragment.arguments = Bundle().apply {
                putString("title", binding.homeAlbumTitleTv1.text.toString())
                putString("singer", binding.homeAlbumSingerTv1.text.toString())
            }
            (context as MainActivity)
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, albumFragment)
                .commitAllowingStateLoss()
        }

        binding.homeBannerVp.adapter = BannerViewPagerAdapter(this)

        return binding.root
    }
}