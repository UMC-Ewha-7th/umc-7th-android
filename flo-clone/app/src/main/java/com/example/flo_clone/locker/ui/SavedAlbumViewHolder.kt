package com.example.flo_clone.locker.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.flo_clone.album.data.Album
import com.example.flo_clone.databinding.ItemSavedAlbumBinding

class SavedAlbumViewHolder(
    private val binding: ItemSavedAlbumBinding,
    private val listener: SavedRecyclerAdapter.OnItemClickListener<Album>
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(album: Album) {

    }
}