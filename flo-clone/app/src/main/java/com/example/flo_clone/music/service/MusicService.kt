package com.example.flo_clone.music.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.flo_clone.music.controller.MediaPlayerController
import com.example.flo_clone.music.controller.MediaPlayerControllerImpl
import com.example.flo_clone.music.data.Song
import com.example.flo_clone.music.data.SongRepository
import com.example.flo_clone.music.timer.TimerManager
import com.example.flo_clone.music.timer.TimerManagerImpl

class MusicService : Service() {
    private val binder = MusicServiceBinder()
    private lateinit var mediaController: MediaPlayerController
    private lateinit var songRepository: SongRepository
    private var timerManager: TimerManager? = null
    private var songs = mutableListOf<Song>()
    private var nowPos: Int = 0

    companion object {
        const val ACTION_UPDATE_PROGRESS = "com.example.flo_clone.UPDATE_PROGRESS"
        const val EXTRA_PROGRESS = "progress"
    }

    override fun onBind(p0: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        songRepository = SongRepository(this)
        mediaController = MediaPlayerControllerImpl(this)

        songRepository.inputDummySongs()
        songs = songRepository.getAllSongs().toMutableList()
        if (songs.isNotEmpty()) initMediaPlayer(songs[nowPos])
    }

    private fun initMediaPlayer(song: Song) {
        mediaController.initMediaPlayer(song)
        setupTimer()
    }

    fun play() {
        songs[nowPos].isPlaying = true
        mediaController.play()
        timerManager?.start()
    }

    fun pause() {
        songs[nowPos].second = mediaController.getCurrentPosition() / 1000
        songs[nowPos].isPlaying = false

        mediaController.pause()
        timerManager?.pause()
    }

    fun moveSong(direct: Int): Song {
        if ((nowPos + direct) in 0 until songs.size) {
            mediaController.release()
            timerManager?.stop()

            songs[nowPos + direct].isPlaying = songs[nowPos].isPlaying
            songs[nowPos].isPlaying = false
            songs[nowPos].second = 0

            nowPos += direct

            songs[nowPos].second = 0
            initMediaPlayer(songs[nowPos])
            if (songs[nowPos].isPlaying) play()
        }

        return songs[nowPos]
    }

    fun getCurSong(): Song = songs[nowPos]

    fun setCurSong(songId: Int, isPlaying: Boolean) {
        songs[nowPos].isPlaying = false
        songs[nowPos].second = 0

        nowPos = getPositionById(songId)
        Log.d("MusicService", "nowPos: $nowPos")
        songs[nowPos].isPlaying = isPlaying
        initMediaPlayer(songs[nowPos])

        if(songs[nowPos].isPlaying) play()
    }

    fun updateSongProgress() {
        songs[nowPos].second = mediaController.getCurrentPosition() / 1000
    }

    fun setSongs(songs: ArrayList<Song>) {
        this.songs = songs.toMutableList()
        Log.d("MusicService", "setSongs: $songs")
    }

    fun seekTo(progress: Int) {
        mediaController.seekTo(progress)
        timerManager?.seekTo(progress)
    }

    private fun getPositionById(songId: Int): Int {
        for (i in 0 until songs.size) {
            if (songs[i].id == songId) return i
        }
        return 0
    }

    private fun setupTimer() {
        if (timerManager != null) timerManager?.stop()

        timerManager = TimerManagerImpl(songs[nowPos].playTime) { progress ->
            sendProgressUpdate(progress)
        }

        (timerManager as TimerManagerImpl).updateProgress((songs[nowPos].second * 1000).toLong())
    }

    private fun sendProgressUpdate(progress: Int) {
        val intent = Intent(ACTION_UPDATE_PROGRESS).apply {
            putExtra(EXTRA_PROGRESS, progress)
        }
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        saveSongInfo()
        mediaController.release()
        timerManager?.stop()
    }

    private fun saveSongInfo() {
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putInt("songId", songs[nowPos].id)
        editor.apply()

        songRepository.initPlayingAndSecond()
        songRepository.updateSong(songs[nowPos])
    }

    inner class MusicServiceBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }
}