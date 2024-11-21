package com.example.week6.ui.home


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week6.R

data class Song(val title: String, val artist: String)

class SongAdapter(private val songList: List<Song>) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.songTitle)
        val artistTextView: TextView = view.findViewById(R.id.songArtist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songList[position]
        holder.titleTextView.text = song.title
        holder.artistTextView.text = song.artist
    }

    override fun getItemCount(): Int = songList.size
}
