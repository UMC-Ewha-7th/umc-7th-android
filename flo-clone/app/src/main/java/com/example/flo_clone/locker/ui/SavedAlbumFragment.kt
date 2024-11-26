package com.example.flo_clone.locker.ui

import androidx.fragment.app.Fragment
import com.example.flo_clone.album.data.Album
import com.example.flo_clone.databinding.FragmentSavedBinding

class SavedAlbumFragment : Fragment() {
    private lateinit var binding: FragmentSavedBinding
    private lateinit var savedAdapter: SavedRecyclerAdapter<Album>
}