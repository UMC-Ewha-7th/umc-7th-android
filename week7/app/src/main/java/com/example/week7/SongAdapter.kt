package com.example.week7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class SongAdapter // Constructor
    (private val songList: List<Song>, private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songList[position]

        holder.titleTextView.text = song.title
        holder.artistTextView.text = song.artist
        holder.likeButton.isSelected = song.isLike

        // 좋아요 버튼 클릭 리스너
        holder.likeButton.setOnClickListener { v: View? ->
            val isLiked = !song.isLike
            song.isLike = isLiked // 좋아요 상태 변경
            holder.likeButton.isSelected = isLiked // 버튼 상태 업데이트
            onItemClickListener.onLikeClicked(song) // 콜백 함수 호출
        }
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView = itemView.findViewById<TextView>(R.id.titleTextView)
        var artistTextView: TextView = itemView.findViewById<TextView>(R.id.artistTextView)
        var likeButton: ImageButton = itemView.findViewById<ImageButton>(R.id.likeButton)
    }

    // 인터페이스 정의
    interface OnItemClickListener {
        fun onLikeClicked(song: Song?) // 좋아요 클릭 이벤트
    }
}
