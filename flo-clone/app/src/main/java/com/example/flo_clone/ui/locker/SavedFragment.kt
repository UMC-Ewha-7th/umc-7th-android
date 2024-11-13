package com.example.flo_clone.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo_clone.Album
import com.example.flo_clone.R
import com.example.flo_clone.databinding.FragmentSavedBinding

class SavedFragment : Fragment() {

    private var albumDatas = ArrayList<Album>()
    private lateinit var binding : FragmentSavedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(inflater, container, false)
        albumDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp2))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp2))
        }

        val lockerAlbumRVAdapter = LockerAlbumRVAdapter(albumDatas)
        binding.lockerMusicAlbumRv.adapter = lockerAlbumRVAdapter
        binding.lockerMusicAlbumRv.layoutManager = LinearLayoutManager(requireActivity())
        return binding.root
    }
}