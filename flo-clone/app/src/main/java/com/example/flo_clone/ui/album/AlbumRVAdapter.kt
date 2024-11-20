package com.example.flo_clone.ui.album

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_clone.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList : ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>(){

    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(album:Album){
        albumList.add(album)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])

        holder.itemView.setOnClickListener{mItemClickListener.onItemClick(albumList[position])
        holder.binding.textView2.setOnClickListener{mItemClickListener.onRemoveAlbum(position)}
        }
    }

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(var binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.textView.text = album.title
            binding.textView2.text = album.Singer
            binding.imageView.setImageResource(album.coverImg!!)
        }

    }
}