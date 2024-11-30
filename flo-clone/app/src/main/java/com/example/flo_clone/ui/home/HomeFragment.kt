package com.example.flo_clone.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo_clone.Album
import com.example.flo_clone.ui.album.AlbumRVAdapter
import com.example.flo_clone.MainActivity
import com.example.flo_clone.R
import com.example.flo.SongDatabase
import com.example.flo_clone.databinding.FragmentHomeBinding
import com.example.flo_clone.ui.album.AlbumFragment
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    private lateinit var songDB : SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homeAlbumImgIv1.setOnClickListener {
//            var albumFragment = AlbumFragment()
//            albumFragment.arguments = Bundle().apply {
//                putString("title", binding.homeAlbumTitleTv1.text.toString())
//                putString("singer", binding.homeAlbumSingerTv1.text.toString())
//            }
//            (context as MainActivity)
//                .supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.nav_host_fragment_activity_main, albumFragment)
//                .commitAllowingStateLoss()
//        }

        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())


        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListner(object: AlbumRVAdapter.MyItemClickListner {
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }

        })

        val bannerAdapter = BannerViewPagerAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity)
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }
}