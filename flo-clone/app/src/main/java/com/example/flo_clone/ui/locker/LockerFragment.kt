package com.example.flo_clone.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo_clone.Play
import com.example.flo_clone.R
import com.example.flo_clone.databinding.FragmentLockerBinding
import com.example.flo_clone.ui.album.Album
import com.example.flo_clone.ui.album.AlbumRVAdapter
import com.example.flo_clone.ui.album.SongRVAdapter
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {
    private lateinit var binding: FragmentLockerBinding
    lateinit var lockerViewPagerAdapter: LockerViewPagerAdapter
    private val tabs = listOf("저장한 곡", "음악파일")
    private var playDatas = ArrayList<Play>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        /*lockerViewPagerAdapter = LockerViewPagerAdapter(requireParentFragment())
        binding.lockerContentVp.adapter = lockerViewPagerAdapter

        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp) { tab, position ->
            tab.text = tabs[position]
        }.attach()*/

        playDatas.apply{
            add(Play("Butter","방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Play("Lilac","아이유 (IU)", R.drawable.img_album_exp2))
            add(Play("Next Level","에스파 (ASEPA)", R.drawable.img_album_exp))
            add(Play("Boy with Luv","방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Play("BBoom BBoom","모모랜드 (MOMOLAND)", R.drawable.img_album_exp))
            add(Play("Weekend","태연 (Tae Yeon)", R.drawable.img_album_exp))
        }

        val songRVAdapter = SongRVAdapter(playDatas)
        binding.lockerRv.adapter = songRVAdapter
        binding.lockerRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

       




        return binding.root
    }
}