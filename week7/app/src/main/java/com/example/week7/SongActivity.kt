package com.example.week7

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SongActivity : AppCompatActivity() {
    private var songId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        // SharedPreferences에서 곡 ID 가져오기
        val sharedPreferences = getSharedPreferences("MusicAppPrefs", MODE_PRIVATE)
        songId = sharedPreferences.getInt("songId", -1)

        // Room DB에서 곡 데이터 가져오기
        val db = AppDatabase.getInstance(this)
        Thread {
            val songDao = db!!.songDao()
            val currentSong = songDao!!.getSongById(songId)
            runOnUiThread {
                if (currentSong != null) {
                    setPlayer(currentSong) // 곡 렌더링
                }
            }
        }.start()
    }

    private fun setPlayer(song: Song) {
        // 곡 데이터와 UI 업데이트
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val artistTextView = findViewById<TextView>(R.id.artistTextView)

        titleTextView.text = song.title
        artistTextView.text = song.artist
    }
}
