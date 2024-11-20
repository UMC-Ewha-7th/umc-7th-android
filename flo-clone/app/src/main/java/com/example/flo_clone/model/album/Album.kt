package com.example.flo_clone.model.album

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Album(
    val title: String = "",
    val singer: String = "",
    val coverImg: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
