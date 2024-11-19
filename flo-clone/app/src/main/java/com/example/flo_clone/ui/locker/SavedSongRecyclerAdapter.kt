package com.example.flo_clone.ui.locker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_clone.R
import com.example.flo_clone.databinding.ItemSongBinding
import com.example.flo_clone.model.song.Song

class SavedSongRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val songs = ArrayList<Song>()

    interface OnItemClickListener {
        fun onRemoveItem(songId: Int)
    }

    private lateinit var listener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int = songs.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SongViewHolder).bind(songs[position])
        holder.binding.songMoreIv.setOnClickListener {
            listener.onRemoveItem(songs[position].id)
            deleteItem(position)
        }
    }

    private fun deleteItem(position: Int) {
        songs.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItems(newItems: ArrayList<Song>) {
        songs.clear()
        songs.addAll(newItems)
        notifyItemRangeChanged(0, newItems.size)
    }

    inner class SongViewHolder(
        val binding: ItemSongBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.songSingerTv.text = song.singer
            binding.songTitleTv.text = song.title
            binding.songAlbumIv.setImageResource(song.coverImg ?: R.drawable.img_album_butter)

            binding.songMoreIv.setOnClickListener {
                deleteItem(adapterPosition)
            }
        }
    }
}