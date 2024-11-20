package com.example.flo_clone.ui.album

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_clone.databinding.ItemSongBinding
import com.example.flo_clone.Play
import com.example.flo_clone.ui.album.AlbumRVAdapter.MyItemClickListener

class SongRVAdapter(val playList: ArrayList<Play>) : RecyclerView.Adapter<SongRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onRemoveSong(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }


    inner class ViewHolder(var binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(playList:Play){
            binding.textView2.text = playList.title
            binding.textView.text = playList.singer
            binding.imageView.setImageResource(playList.coverImg!!)
        }

    }



    @SuppressLint("NotifyDataSetChanged")
    fun addItem(play:Play){
        playList.add(play)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int){
        playList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SongRVAdapter.ViewHolder {
        val binding: ItemSongBinding = ItemSongBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = playList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(playList[position])
        holder.binding.imageView3.setOnClickListener{mItemClickListener.onRemoveSong(position)}
    }
}