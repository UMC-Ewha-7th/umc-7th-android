package com.example.flo_clone.music.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SongDao {
    @Insert
    fun insert(song: Song)

    @Update
    fun update(song: Song)

    @Delete
    fun delete(song: Song)

    fun deleteAll() {
        getAll().forEach {
            delete(it)
        }
    }

    @Query("SELECT * FROM Song")
    fun getAll(): List<Song>

    @Query("SELECT * FROM Song WHERE id = :id")
    fun getSongById(id: Int): Song

    @Query("SELECT * FROM Song WHERE albumId = :albumId")
    fun getSongsByAlbumId(albumId: Int): List<Song>

    @Query("UPDATE Song SET isLike = :isLike WHERE id = :id")
    fun updateLikeById(id: Int, isLike: Boolean)

    @Query("SELECT * FROM Song WHERE isLike = :isLike")
    fun getLikedSongs(isLike: Boolean): List<Song>

    @Query("UPDATE Song SET isPlaying = :isPlaying, second = :second")
    fun initPlayingAndSecond(isPlaying: Boolean, second: Int)
}