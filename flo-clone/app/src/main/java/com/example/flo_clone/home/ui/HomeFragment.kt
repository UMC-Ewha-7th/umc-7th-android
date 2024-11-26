package com.example.flo_clone.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo_clone.MainActivity
import com.example.flo_clone.R
import com.example.flo_clone.album.data.Album
import com.example.flo_clone.album.data.AlbumRepository
import com.example.flo_clone.album.ui.AlbumFragment
import com.example.flo_clone.databinding.FragmentHomeBinding
import com.example.flo_clone.music.data.Song
import com.example.flo_clone.music.data.SongRepository
import com.example.flo_clone.user.ui.LoginActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var albumRvAdapter: TodayAlbumRecyclerAdapter
    private lateinit var albumRepository: AlbumRepository
    private lateinit var songRepository: SongRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        albumRepository = AlbumRepository(requireContext())
        songRepository = SongRepository(requireContext())

        albumRepository.inputDummyAlbums()

        setupRecyclerView()

        // 배너 ViewPager 어댑터 적용
        val bannerAdapter = BannerViewPagerAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter

        binding.homePanelBtnSettingIv.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        albumRvAdapter = TodayAlbumRecyclerAdapter()
        binding.homeTodayMusicTotalRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRvAdapter.setOnItemClickListener(object :
            TodayAlbumRecyclerAdapter.OnItemClickListener {
            override fun albumPlayClickListener(album: Album) {
                val songs = songRepository.getSongsByAlbumId(album.id)

                if (songs.isEmpty()) return
                (context as MainActivity).setSongsAndPlay(songs as ArrayList<Song>)
            }

            override fun albumClickListener(album: Album) {
                val albumFragment = AlbumFragment()
                albumFragment.arguments = Bundle().apply {
                    putInt("albumId", album.id)
                }
                (context as MainActivity)
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, albumFragment)
                    .commitAllowingStateLoss()
            }
        })
        binding.homeTodayMusicTotalRv.adapter = albumRvAdapter

        albumRvAdapter.addItems(albumRepository.getAllAlbums() as ArrayList<Album>)
    }
}