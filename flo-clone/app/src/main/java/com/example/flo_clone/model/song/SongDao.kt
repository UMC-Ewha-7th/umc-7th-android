package com.example.flo_clone.model.song

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

    @Query("SELECT * FROM Song")
    fun getAll(): List<Song>

    @Query("SELECT * FROM Song WHERE id = :id")
    fun getSong(id: Int): Song

    @Query("UPDATE Song SET isLike = :isLike WHERE id = :id")
    fun updateLikeById(id: Int, isLike: Boolean)

    @Query("SELECT * FROM Song WHERE isLike = :isLike")
    fun getLikedSongs(isLike: Boolean): List<Song>
}