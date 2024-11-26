package com.example.flo_clone.locker.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo_clone.MainActivity
import com.example.flo_clone.R
import com.example.flo_clone.databinding.FragmentSavedBinding
import com.example.flo_clone.music.data.Song
import com.example.flo_clone.common.FloDatabase
import com.example.flo_clone.music.data.SongRepository
import com.google.android.material.bottomsheet.BottomSheetBehavior

class SavedSongFragment : Fragment() {
    private lateinit var binding: FragmentSavedBinding
    private lateinit var savedAdapter: SavedSongRecyclerAdapter
    private lateinit var songRepository: SongRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBinding.inflate(inflater, container, false)
        songRepository = SongRepository(requireContext())

        setupRecyclerView()
        changeSelectStatus(false)

        binding.savedBtnSelectAllTv.setOnClickListener {
            if (binding.savedBtnSelectAllTv.text == getString(R.string.saved_btn_deselct_all_tv)) {
                savedAdapter.deselectAll()
                changeSelectStatus(false)
            } else {
                savedAdapter.selectAll()
                changeSelectStatus(true)
            }
        }

        binding.sheetDeleteIv.setOnClickListener {
            savedAdapter.deleteSelectedItems()
            changeSelectStatus(false)
        }

        return binding.root
    }

    private fun changeSelectStatus(isSelected: Boolean) {
        val bottomBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        if (isSelected) {
            binding.savedBtnSelectAllTv.text = getString(R.string.saved_btn_deselct_all_tv)
            binding.savedBtnSelectAllIv.setColorFilter(Color.parseColor("#3F3FFF"))
            binding.savedBtnSelectAllTv.setTextColor(Color.parseColor("#3F3FFF"))

            (activity as MainActivity).showBottomSheet(true)
            binding.bottomSheet.visibility = View.VISIBLE
            bottomBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            binding.savedBtnSelectAllTv.text = getString(R.string.saved_btn_select_all_tv)
            binding.savedBtnSelectAllIv.setColorFilter(Color.parseColor("#000000"))
            binding.savedBtnSelectAllTv.setTextColor(Color.parseColor("#000000"))

            bottomBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            binding.bottomSheet.visibility = View.GONE
            (activity as MainActivity).showBottomSheet(false)
        }
    }

    override fun onResume() {
        super.onResume()
        savedAdapter.addItems(songRepository.getLikedSongs(true) as ArrayList<Song>)
    }

    private fun setupRecyclerView() {
        savedAdapter = SavedSongRecyclerAdapter()
        binding.savedRv.layoutManager = LinearLayoutManager(context)

        savedAdapter.setOnItemClickListener(object : SavedSongRecyclerAdapter.OnItemClickListener {
            override fun onRemoveItem(songId: Int) {
                songRepository.updateLikeById(songId, false)
            }

            override fun onItemClicked(isSelected: Boolean) {
                changeSelectStatus(isSelected)
            }
        })
        binding.savedRv.adapter = savedAdapter

        savedAdapter.addItems(songRepository.getLikedSongs(true) as ArrayList<Song>)
    }
}