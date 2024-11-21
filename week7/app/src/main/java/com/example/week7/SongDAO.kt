package com.example.week7

import android.app.DownloadManager
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update

@Dao
interface SongDao {
    @Insert
    fun insertSongs(songs: List<Song?>?)

    @get:DownloadManager.Query("SELECT * FROM songs")
    val allSongs: List<Song?>?

    @DownloadManager.Query("SELECT * FROM songs WHERE id = :id")
    fun getSongById(id: Int): Song?

    @Update
    fun updateSong(song: Song?)

    @get:DownloadManager.Query("SELECT * FROM songs WHERE isLike = 1")
    val likedSongs: List<Song?>?
}
