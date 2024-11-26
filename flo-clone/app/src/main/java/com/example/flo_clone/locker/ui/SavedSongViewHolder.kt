package com.example.flo_clone.locker.ui

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_clone.R
import com.example.flo_clone.databinding.ItemSongBinding
import com.example.flo_clone.music.data.Song

class SavedSongViewHolder(
    val binding: ItemSongBinding,
    private val listener: SavedRecyclerAdapter.OnItemClickListener<Song>
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(song: Song) {
        binding.songSingerTv.text = song.singer
        binding.songTitleTv.text = song.title
        binding.songAlbumIv.setImageResource(song.coverImg ?: R.drawable.img_album_butter)

        binding.songMoreIv.setOnClickListener {
            listener.onRemoveItem(song)
        }

        binding.songItemCl.setOnClickListener {
            listener.onItemClicked(song)
        }

        if (listener.isItemSelected(song)) {
            binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.light_gray))
        } else {
            binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.light_gray))
        }
    }
}