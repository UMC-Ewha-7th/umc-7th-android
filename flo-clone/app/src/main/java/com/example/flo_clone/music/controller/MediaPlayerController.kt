package com.example.flo_clone.music.controller

import com.example.flo_clone.music.data.Song

interface MediaPlayerController {
    fun initMediaPlayer(song: Song)
    fun play()
    fun pause()
    fun seekTo(position: Int)
    fun isPlaying(): Boolean
    fun release()
    fun getCurrentPosition(): Int
}