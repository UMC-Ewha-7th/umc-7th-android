package com.example.flo_clone.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_clone.Album
import com.example.flo_clone.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListner{
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)

        companion object {
            fun onItemClick(album: Album) {

            }

            fun onRemoveAlbum(position: Int) {

            }
        }
    }

    private lateinit var myItemClickListner: MyItemClickListner
    fun setMyItemClickListner(itemClickListner: MyItemClickListner) {
        myItemClickListner = itemClickListner
    }

    fun addItem(album: Album) {
        albumList.add(album)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener{ myItemClickListner.onItemClick(albumList[position]) }
//        holder.binding.itemAlbumTitleTv.setOnClickListener{ MyItemClickListner.onRemoveAlbum(position)}
    }

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }

    }
}