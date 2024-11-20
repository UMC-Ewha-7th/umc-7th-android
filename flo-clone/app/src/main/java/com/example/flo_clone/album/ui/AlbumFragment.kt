package com.example.flo_clone.album.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo_clone.MainActivity
import com.example.flo_clone.R
import com.example.flo_clone.databinding.FragmentAlbumBinding
import com.example.flo_clone.album.data.Album
import com.example.flo_clone.music.data.SongDatabase
import com.example.flo_clone.home.ui.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    private lateinit var songDB: SongDatabase
    private var album: Album? = null

    private val tabs = listOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!

        if (arguments != null && arguments?.getInt("albumId") != null) {
            val albumId = requireArguments().getInt("albumId")
            album = songDB.albumDao().getAlbumById(albumId)
            binding.albumMusicTitleTv.text = album!!.title
            binding.albumSingerNameTv.text = album!!.singer
            binding.albumAlbumIv.setImageResource(album!!.coverImg)
        }

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity)
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, HomeFragment())
                .commitAllowingStateLoss()
        }

        binding.albumContentVp.adapter = AlbumViewPagerAdapter(this)
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) { tab, position ->
            tab.text = tabs[position]
        }.attach()

        return binding.root
    }
}