package com.example.myapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.MusicitemBinding
import com.example.myapplication.music

class HomeFragmentRVAdapter(private val musicList : ArrayList<music>): RecyclerView.Adapter<HomeFragmentRVAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentRVAdapter.ViewHolder {
        val binding = MusicitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeFragmentRVAdapter.ViewHolder, position: Int) {
        holder.bind(musicList[position])
    }

    override fun getItemCount(): Int = musicList.size

    inner class ViewHolder(var binding: MusicitemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(music:music){
            binding.album.text = music.album
            binding.singer.text = music.singer
            binding.imageView.setImageResource(music.coverImg!!)
        }

    }
}