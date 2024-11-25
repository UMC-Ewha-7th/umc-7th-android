package com.example.flo_clone.music.controller

import android.content.Context
import android.media.MediaPlayer
import com.example.flo_clone.music.data.Song

class MediaPlayerControllerImpl(private val context: Context) : MediaPlayerController {
    private var mediaPlayer: MediaPlayer? = null

    override fun initMediaPlayer(song: Song) {
        if(mediaPlayer != null) {
            release()
        }

        val music = context.resources.getIdentifier(song.music, "raw", context.packageName)
        mediaPlayer = MediaPlayer.create(context, music).apply {
            seekTo(song.second * 1000)
        }

        if (song.isPlaying) {
            play()
        }
    }

    override fun play() {
        mediaPlayer?.start()
    }

    override fun pause() {
        mediaPlayer?.pause()
    }

    override fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    override fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun getCurrentPosition(): Int = mediaPlayer?.currentPosition ?: 0
}