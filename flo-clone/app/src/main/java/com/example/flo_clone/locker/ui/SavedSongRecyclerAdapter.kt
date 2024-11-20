package com.example.flo_clone.locker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_clone.R
import com.example.flo_clone.databinding.ItemSongBinding
import com.example.flo_clone.music.data.Song

class SavedSongRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val songs = ArrayList<Song>()
    private val selectedItems = ArrayList<Song>()
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onRemoveItem(songId: Int)
        fun onItemClicked(isSelected: Boolean)
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

        holder.binding.songItemCl.setOnClickListener {
            if (selectedItems.contains(songs[position])) {
                selectedItems.remove(songs[position])

                if (selectedItems.isEmpty()) {
                    listener.onItemClicked(false)
                }
            } else if (selectedItems.isEmpty()){
                listener.onItemClicked(true)
                selectedItems.add(songs[position])
            } else {
                selectedItems.add(songs[position])
            }
            notifyItemChanged(position)
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun addItems(newItems: ArrayList<Song>) {
        songs.clear()
        songs.addAll(newItems)
        notifyItemRangeChanged(0, newItems.size)
    }

    fun selectAll() {
        selectedItems.clear()
        selectedItems.addAll(songs)
        notifyDataSetChanged()
    }

    fun deselectAll() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun deleteSelectedItems() {
        selectedItems.forEach {
            listener.onRemoveItem(it.id)
        }
        songs.removeAll(selectedItems.toSet())
        selectedItems.clear()
        notifyDataSetChanged()
    }

    private fun deleteItem(position: Int) {
        songs.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class SongViewHolder(
        val binding: ItemSongBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.songSingerTv.text = song.singer
            binding.songTitleTv.text = song.title
            binding.songAlbumIv.setImageResource(song.coverImg ?: R.drawable.img_album_butter)

            if (selectedItems.contains(song)) {
                binding.root.setBackgroundColor(binding.root.context.getColor(R.color.light_gray))
            } else {
                binding.root.setBackgroundColor(binding.root.context.getColor(R.color.white))
            }
        }
    }
}