package com.example.flo_clone.locker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_clone.album.data.Album
import com.example.flo_clone.databinding.ItemSavedAlbumBinding
import com.example.flo_clone.databinding.ItemSongBinding
import com.example.flo_clone.music.data.Song

class SavedRecyclerAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = ArrayList<T>()
    private val selectedItems = ArrayList<T>()
    private lateinit var listener: OnItemActionListener<T>

    companion object {
        private const val TYPE_SONG = 0
        private const val TYPE_ALBUM = 1
    }

    interface OnItemActionListener<T> {
        fun onItemRemoved(item: T)
        fun onItemClicked(item: T)
        fun isItemSelected(item: T): Boolean
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Song -> TYPE_SONG
            is Album -> TYPE_ALBUM
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SONG -> createSongViewHolder(parent)
            TYPE_ALBUM -> createAlbumViewHolder(parent)
            else -> throw IllegalArgumentException("Invalid type of view $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SavedSongViewHolder -> {
                val song = items[position] as Song
                holder.bind(song)
            }

            is SavedAlbumViewHolder -> {
                val album = items[position] as Album
                holder.bind(album)
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemActionListener<T>) {
        this.listener = listener
    }

    fun addItems(newItems: ArrayList<T>) {
        items.clear()
        items.addAll(newItems)
        notifyItemRangeChanged(0, items.size)
    }

    fun selectAll() {
        selectedItems.clear()
        selectedItems.addAll(items)
        notifyItemRangeChanged(0, items.size)
    }

    fun selectItem(item: T) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
        } else {
            selectedItems.add(item)
        }
        notifyItemChanged(items.indexOf(item))
    }

    fun isItemSelected(item: T): Boolean {
        return selectedItems.contains(item)
    }

    fun isSelectedItemNotEmpty(): Boolean {
        return selectedItems.isNotEmpty()
    }

    fun deselectAll() {
        selectedItems.clear()
        notifyItemRangeChanged(0, items.size)
    }

    fun deleteSelectedItems() {
        selectedItems.forEach {
            listener.onItemRemoved(it)
        }
        items.removeAll(selectedItems.toSet())
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun deleteItem(item: T) {
        notifyItemRemoved(items.indexOf(item))
        items.remove(item)
    }

    private fun createSongViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SavedSongViewHolder(binding, listener as OnItemActionListener<Song>)
    }

    private fun createAlbumViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemSavedAlbumBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SavedAlbumViewHolder(binding, listener as OnItemActionListener<Album>)
    }
}