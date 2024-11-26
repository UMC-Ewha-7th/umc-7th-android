package com.example.flo_clone.locker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo_clone.MainActivity
import com.example.flo_clone.R
import com.example.flo_clone.album.data.Album
import com.example.flo_clone.album.data.AlbumRepository
import com.example.flo_clone.databinding.FragmentSavedBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class SavedAlbumFragment : Fragment() {
    private lateinit var binding: FragmentSavedBinding
    private lateinit var savedAdapter: SavedRecyclerAdapter<Album>
    private lateinit var albumRepository: AlbumRepository
    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBinding.inflate(inflater, container, false)
        val spf = requireContext().getSharedPreferences("user", MODE_PRIVATE)
        userId = spf.getInt("userIdx", 0)

        albumRepository = AlbumRepository(requireContext())
        savedAdapter = SavedRecyclerAdapter()

        if (userId == 0) {
            binding.savedListCl.visibility = View.GONE
            binding.savedNoMusicTv.visibility = View.VISIBLE
            binding.savedNoMusicTv.text = "로그인 후 이용해주세요."
        }

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

            if (savedAdapter.itemCount == 0) {
                setNoAlbumView()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (userId != 0) {
            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() {
        binding.savedRv.layoutManager = LinearLayoutManager(context)

        savedAdapter.setOnItemClickListener(object :
            SavedRecyclerAdapter.OnItemActionListener<Album> {
            override fun onItemRemoved(item: Album) {
                albumRepository.deleteLikeAlbum(userId, item.id)
                savedAdapter.deleteItem(item)

            }

            override fun onItemClicked(item: Album) {
                savedAdapter.selectItem(item)
                changeSelectStatus(savedAdapter.isSelectedItemNotEmpty())
            }

            override fun isItemSelected(item: Album): Boolean {
                return savedAdapter.isItemSelected(item)
            }
        })

        binding.savedRv.adapter = savedAdapter

        val albumList = albumRepository.getLikedAlbums(userId) as ArrayList<Album>

        if (albumList.isEmpty()) {
            setNoAlbumView()
        } else {
            binding.savedListCl.visibility = View.VISIBLE
            binding.savedNoMusicTv.visibility = View.GONE
            savedAdapter.addItems(albumList)
        }
    }

    private fun changeSelectStatus(isSelected: Boolean) {
        val bottomBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        if (isSelected) {
            binding.savedBtnSelectAllTv.text = getString(R.string.saved_btn_deselct_all_tv)
            binding.savedBtnSelectAllIv.setColorFilter(
                ContextCompat.getColor(binding.root.context, R.color.select_color)
            )
            binding.savedBtnSelectAllTv.setTextColor(
                ContextCompat.getColor(binding.root.context, R.color.select_color)
            )

            (activity as MainActivity).showBottomSheet(true)
            binding.bottomSheet.visibility = View.VISIBLE
            bottomBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            binding.savedBtnSelectAllTv.text = getString(R.string.saved_btn_select_all_tv)
            binding.savedBtnSelectAllIv.setColorFilter(
                ContextCompat.getColor(binding.root.context, R.color.black)
            )
            binding.savedBtnSelectAllTv.setTextColor(
                ContextCompat.getColor(binding.root.context, R.color.black)
            )

            bottomBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            binding.bottomSheet.visibility = View.GONE
            (activity as MainActivity).showBottomSheet(false)
        }
    }

    private fun setNoAlbumView() {
        binding.savedListCl.visibility = View.GONE
        binding.savedNoMusicTv.text = "저장된 앨범이 없습니다."
        binding.savedNoMusicTv.visibility = View.VISIBLE
    }
}