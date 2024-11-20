package com.example.flo_clone.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_clone.databinding.ItemAlbumBinding
import com.example.flo_clone.model.album.Album

class TodayAlbumRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val albums = ArrayList<Album>()
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun albumClickListener(album: Album)
        fun albumPlayClickListener(album: Album)
    }

    override fun getItemCount(): Int = albums.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AlbumViewHolder).bind(albums[position])
        holder.binding.albumPlayIv.setOnClickListener {
            listener.albumPlayClickListener(albums[position])
        }
        holder.binding.albumImgIv.setOnClickListener {
            listener.albumClickListener(albums[position])
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun addItems(newItems: ArrayList<Album>) {
        albums.clear()
        albums.addAll(newItems)
        notifyItemRangeChanged(0, newItems.size)
    }

    inner class AlbumViewHolder(
        val binding: ItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.albumTitleTv.text = album.title
            binding.albumSingerTv.text = album.singer
            binding.albumImgIv.setImageResource(album.coverImg)
        }
    }
}