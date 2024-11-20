package com.example.flo_clone.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.flo_clone.R
import com.example.flo_clone.model.album.Album
import com.example.flo_clone.model.song.Song
import com.example.flo_clone.model.song.SongDatabase

class MusicService : Service() {
    private val binder = MusicServiceBinder()
    private lateinit var songDB: SongDatabase
    private val songs = arrayListOf<Song>()
    private var nowPos: Int = 0
    private var timer: Timer? = null
    private var mediaPlayer: MediaPlayer? = null

    companion object {
        const val ACTION_UPDATE_PROGRESS = "com.example.flo_clone.UPDATE_PROGRESS"
        const val EXTRA_PROGRESS = "progress"
    }

    override fun onBind(p0: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        inputDummySongs()
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getAll())

        Log.d("MusicService", "songs: $songs")
    }

    override fun onDestroy() {
        super.onDestroy()
        resetTimer()
        mediaPlayer?.release()
        mediaPlayer = null

        updateSong()
    }

    fun fetchSongs() {
        songs.clear()
        songs.addAll(songDB.songDao().getAll())
    }

    fun initSong(): Song {
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)
        initMediaPlayer()

        Log.d("MusicService", "nowPos: $nowPos")
        return songs[nowPos]
    }

    fun play() {
        if (mediaPlayer == null) {
            initMediaPlayer()
        }
        mediaPlayer?.start()
        startTimer()
        Log.d("MusicService", "음악 재생: ${songs[nowPos].title}")
    }

    fun pause() {
        mediaPlayer?.pause()
        timer?.isPlaying = false
        Log.d("MusicService", "음악 정지: ${songs[nowPos].title}")
    }

    fun getCurSong(): Song {
        return songs[nowPos]
    }

    fun updateSongList(songList: ArrayList<Song>) {
        songs.clear()
        songs.addAll(songList)
        nowPos = 0

        mediaPlayer?.release()
        mediaPlayer = null
        resetTimer()

        play()
    }

    fun updateSong() {
        if (mediaPlayer != null) {
            songs[nowPos].second = mediaPlayer?.currentPosition!! / 1000
            songs[nowPos].isPlaying = mediaPlayer?.isPlaying!!
            Thread {
                songDB.songDao().update(songs[nowPos])
            }.start()
        }

        val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
        editor.putInt("songId", songs[nowPos].id)
        editor.apply()
    }

    fun moveSong(direct: Int): Song {
        if (nowPos + direct < 0) {
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
        } else if (nowPos + direct >= songs.size) {
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
        } else {
            mediaPlayer?.release()
            mediaPlayer = null
            resetTimer()

            songs[nowPos].second = 0
            songs[nowPos + direct].isPlaying = songs[nowPos].isPlaying
            songs[nowPos + direct].second = 0

            nowPos += direct

            if (songs[nowPos].isPlaying) {
                play()
            }
        }
        return songs[nowPos]
    }

    fun seekTo(progress: Int) {
        mediaPlayer?.seekTo((progress * songs[nowPos].playTime / 100))
        timer?.setMills((progress * songs[nowPos].playTime / 100).toFloat())
    }

    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)!!
        val songs: List<Song> = songDB.songDao().getAll()

        if (songs.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                "ONCE",
                "유다빈밴드",
                R.drawable.img_album_once
            )
        )

        songDB.albumDao().insert(
            Album(
                "LETTER",
                "유다빈밴드",
                R.drawable.img_album_letter
            )
        )

        songDB.albumDao().insert(
            Album(
                "항해",
                "유다빈밴드",
                R.drawable.img_album_voyage
            )
        )

        songDB.albumDao().insert(
            Album(
                "IU 5th Album 'LILAC'",
                "아이유 (IU)",
                R.drawable.img_album_lilac
            )
        )

        songDB.albumDao().insert(
            Album(
                "치얼업 (Original Soundtrack Part.5)",
                "유다빈밴드",
                R.drawable.img_album_cheerup_ost
            )
        )

        songDB.albumDao().insert(
            Album(
                "Butter (feat. Megan Thee Stallion)",
                "BTS (방탄소년단)",
                R.drawable.img_album_butter
            )
        )

        songDB.songDao().insert(
            Song(
                "Calling",
                "유다빈밴드",
                0,
                194,
                false,
                "music_calling",
                R.drawable.img_album_once,
                false,
                1
            )
        )

        songDB.songDao().insert(
            Song(
                "ONCE",
                "유다빈밴드",
                0,
                216,
                false,
                "music_once",
                R.drawable.img_album_once,
                false,
                1
            )
        )

        songDB.songDao().insert(
            Song(
                "땅",
                "유다빈밴드",
                0,
                270,
                false,
                "music_ground",
                R.drawable.img_album_letter,
                false,
                2
            )
        )

        songDB.songDao().insert(
            Song(
                "LETTER",
                "유다빈밴드",
                0,
                207,
                false,
                "music_letter",
                R.drawable.img_album_letter,
                false,
                2
            )
        )

        songDB.songDao().insert(
            Song(
                "항해",
                "유다빈밴드",
                0,
                226,
                false,
                "music_voyage",
                R.drawable.img_album_voyage,
                false,
                3
            )
        )

        songDB.songDao().insert(
            Song(
                "LILAC (라일락)",
                "아이유 (IU)",
                0,
                217,
                false,
                "music_lilac",
                R.drawable.img_album_lilac,
                false,
                4
            )
        )

        songDB.songDao().insert(
            Song(
                "오늘이야 (Good Day)",
                "유다빈밴드",
                0,
                231,
                false,
                "music_good_day",
                R.drawable.img_album_cheerup_ost,
                false,
                5
            )
        )

        songDB.songDao().insert(
            Song(
                "오늘이야 (Good Day) (Inst.ver)",
                "유다빈밴드",
                0,
                231,
                false,
                "music_good_day_inst",
                R.drawable.img_album_cheerup_ost,
                false,
                5
            )
        )

        val _songs = songDB.songDao().getAll()
        Log.d("DB data", _songs.toString())

        val _albums = songDB.albumDao().getAll()
        Log.d("DB data", _albums.toString())
    }

    private fun initMediaPlayer() {
        val music = resources.getIdentifier(songs[nowPos].music, "raw", packageName)
        mediaPlayer = MediaPlayer.create(this, music)
        mediaPlayer?.seekTo(songs[nowPos].second * 1000)
    }

    private fun getPlayingSongPosition(songId: Int): Int {
        for (i in 0 until songs.size) {
            if (songs[i].id == songId) return i
        }
        return 0
    }

    private fun startTimer() {
        if (timer == null) {
            timer = Timer(songs[nowPos].playTime, true)
            timer?.start()
        } else {
            timer?.isPlaying = true
        }
    }

    private fun resetTimer() {
        timer?.isPlaying = false
        timer?.interrupt()
        timer = null
    }

    inner class Timer(
        private val playTime: Int,
        var isPlaying: Boolean = true
    ) : Thread() {
        private var mills: Float = (songs[nowPos].second * 1000).toFloat()

        override fun run() {
            try {
                while (true) {
                    if (isPlaying) {
                        sleep(50)
                        mills += 50
                        sendProgressUpdate(((mills / playTime) * 100).toInt())
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("Timer", "Timer 스레드 종료.. ${e.message}")
            }
        }

        private fun sendProgressUpdate(progress: Int) {
            val intent = Intent(ACTION_UPDATE_PROGRESS)
            intent.putExtra(EXTRA_PROGRESS, progress)
            sendBroadcast(intent)
        }

        fun setMills(mills: Float) {
            this.mills = mills
        }
    }

    inner class MusicServiceBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }
}