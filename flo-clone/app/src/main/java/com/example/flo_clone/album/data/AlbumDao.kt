package com.example.flo_clone.album.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlbumDao {
    @Insert
    fun insertAlbum(album: Album)

    @Insert
    fun insertLikeAlbum(albumLike: AlbumLike)

    @Update
    fun updateAlbum(album: Album)

    @Delete
    fun deleteAlbum(album: Album)

    @Delete
    fun deleteLikeAlbum(albumLike: AlbumLike)

    @Query("SELECT * FROM Album")
    fun getAllAlbums(): List<Album>

    @Query("SELECT * FROM Album WHERE id = :id")
    fun getAlbumById(id: Int): Album

    @Query("SELECT EXISTS (SELECT * FROM AlbumLike WHERE userId = :userId AND albumId = :albumId)")
    fun isLikedAlbum(userId: Int, albumId: Int): Boolean
}