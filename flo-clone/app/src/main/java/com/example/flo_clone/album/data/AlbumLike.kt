package com.example.flo_clone.album.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlbumLike(
    val albumId: Int,
    val userId: Int
) {
    @PrimaryKey(autoGenerate = true) val id: Int = 0
}
