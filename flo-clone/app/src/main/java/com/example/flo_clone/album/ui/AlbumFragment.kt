package com.example.flo_clone.album.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo_clone.MainActivity
import com.example.flo_clone.R
import com.example.flo_clone.album.data.Album
import com.example.flo_clone.album.data.AlbumRepository
import com.example.flo_clone.databinding.FragmentAlbumBinding
import com.example.flo_clone.home.ui.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    private lateinit var albumRepository: AlbumRepository
    private var album: Album? = null
    private var userId: Int = 0
    private var isLike: Boolean = false

    private val tabs = listOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        albumRepository = AlbumRepository(requireContext())

        initView()

        if (arguments != null && arguments?.getInt("albumId") != null) {
            val albumId = requireArguments().getInt("albumId")
            album = albumRepository.getAlbumById(albumId)
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

        binding.albumLikeIv.setOnClickListener {
            likeAlbum()
        }

        binding.albumContentVp.adapter = AlbumViewPagerAdapter(this)
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) { tab, position ->
            tab.text = tabs[position]
        }.attach()

        return binding.root
    }

    private fun initView() {
        if (arguments == null || arguments?.getInt("albumId") == null) {
            (context as MainActivity)
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, HomeFragment())
                .commitAllowingStateLoss()
        }

        val albumId = requireArguments().getInt("albumId")
        album = albumRepository.getAlbumById(albumId)
        binding.albumMusicTitleTv.text = album!!.title
        binding.albumSingerNameTv.text = album!!.singer
        binding.albumAlbumIv.setImageResource(album!!.coverImg)

        val spf = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        userId = spf.getInt("userIdx", 0)
        isLike = albumRepository.isLikedAlbum(userId, albumId)

        if (isLike) {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun likeAlbum() {
        if (userId == 0) {
            Toast.makeText(requireContext(), "로그인 후 이용해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        isLike = !isLike

        if (isLike) {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
            albumRepository.insertLikeAlbum(userId, album!!.id)
        } else {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
            albumRepository.deleteLikeAlbum(userId, album!!.id)
        }
    }
}