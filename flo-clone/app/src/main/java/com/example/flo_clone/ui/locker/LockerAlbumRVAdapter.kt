package com.example.flo_clone.ui.locker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_clone.Album
import com.example.flo_clone.databinding.ItemLockerAlbumBinding
import com.example.flo_clone.ui.album.AlbumRVAdapter.MyItemClickListner

class LockerAlbumRVAdapter (private val albumList: ArrayList<Album>) : RecyclerView.Adapter<LockerAlbumRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LockerAlbumRVAdapter.ViewHolder {
        val binding: ItemLockerAlbumBinding = ItemLockerAlbumBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerAlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener{
            MyItemClickListner.onItemClick(albumList[position])
        }

        holder.binding.itemLockerAlbumMoreIv.setOnClickListener{
            MyItemClickListner.onRemoveAlbum(position)
        }
        
    }

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemLockerAlbumBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.itemLockerAlbumTitleTv.text = album.title
            binding.itemLockerAlbumSingerTv.text = album.singer
            binding.itemLockerAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }
}