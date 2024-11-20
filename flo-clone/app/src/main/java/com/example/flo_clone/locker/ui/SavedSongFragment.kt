package com.example.flo_clone.locker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo_clone.databinding.FragmentSavedBinding
import com.example.flo_clone.music.data.Song
import com.example.flo_clone.music.data.SongDatabase

class SavedSongFragment : Fragment() {
    private lateinit var binding: FragmentSavedBinding
    private lateinit var savedAdapter: SavedSongRecyclerAdapter
    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!

        setupRecyclerView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        savedAdapter.addItems(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }

    private fun setupRecyclerView() {
        savedAdapter = SavedSongRecyclerAdapter()
        binding.savedRv.layoutManager = LinearLayoutManager(context)

        savedAdapter.setOnItemClickListener(object : SavedSongRecyclerAdapter.OnItemClickListener {
            override fun onRemoveItem(songId: Int) {
                songDB.songDao().updateLikeById(songId, false)
            }
        })
        binding.savedRv.adapter = savedAdapter

        savedAdapter.addItems(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }
}