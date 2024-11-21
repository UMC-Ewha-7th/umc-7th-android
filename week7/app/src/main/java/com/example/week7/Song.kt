package com.example.week7

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
class Song // Constructor
    (var title: String, var artist: String, var isLike: Boolean) {
    // Getters and Setters
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 // Primary Key
}

