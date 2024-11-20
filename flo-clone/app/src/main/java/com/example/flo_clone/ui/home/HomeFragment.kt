package com.example.flo_clone.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo_clone.MainActivity
import com.example.flo_clone.R
import com.example.flo_clone.databinding.FragmentHomeBinding
import com.example.flo_clone.model.album.Album
import com.example.flo_clone.model.song.Song
import com.example.flo_clone.model.song.SongDatabase
import com.example.flo_clone.ui.album.AlbumFragment

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var albumRvAdapter: TodayAlbumRecyclerAdapter
    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!

        setupRecyclerView()

        // 배너 ViewPager 어댑터 적용
        val bannerAdapter = BannerViewPagerAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter

        return binding.root
    }

    private fun setupRecyclerView() {
        albumRvAdapter = TodayAlbumRecyclerAdapter()
        binding.homeTodayMusicTotalRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRvAdapter.setOnItemClickListener(object : TodayAlbumRecyclerAdapter.OnItemClickListener {
            override fun albumPlayClickListener(album: Album) {
                val songs = songDB.songDao().getSongsByAlbumId(album.id)

                if(songs.isEmpty()) return
                (context as MainActivity).updateSongList(songs as ArrayList<Song>)
                songs[0].isPlaying = true
                (context as MainActivity).setMiniPlayer(songs[0])
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

        albumRvAdapter.addItems(songDB.albumDao().getAll() as ArrayList<Album>)
    }
}