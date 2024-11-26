package com.example.flo_clone.locker.ui

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_clone.R
import com.example.flo_clone.album.data.Album
import com.example.flo_clone.databinding.ItemSavedAlbumBinding

class SavedAlbumViewHolder(
    private val binding: ItemSavedAlbumBinding,
    private val listener: SavedRecyclerAdapter.OnItemActionListener<Album>
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(album: Album) {
        binding.savedAlbumTitleTv.text = album.title
        binding.savedAlbumSingerTv.text = album.singer
        binding.savedAlbumImgIv.setImageResource(album.coverImg)

        binding.savedAlbumItemCl.setOnClickListener {
            listener.onItemClicked(album)
        }

        if (listener.isItemSelected(album)) {
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.light_gray
                )
            )
        } else {
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.white
                )
            )
        }
    }
}