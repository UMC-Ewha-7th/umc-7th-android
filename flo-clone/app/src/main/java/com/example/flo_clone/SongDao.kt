package com.example.flo_clone

import androidx.room.*

interface SongDao {
    @Insert
    fun insert(song:Song)

    @Update
    fun update(song:Song)

    @Delete
    fun delete(song:Song)

    @Query("SELECT * FROM songTable")
    fun getSongs(): List<Song>

    @Query("SELECT * FROM SongTable WHERE id = :id")
    fun getSong(id: Int): Song

    @Query("UPDATE SongTable SET isLike= :isLike WHERE id = :id")
    fun updateIsLikeById(isLike: Boolean,id: Int)

    @Query("SELECT * FROM SongTable WHERE isLike= :isLike")
    fun getLikedSongs(isLike: Boolean): List<Song>
}