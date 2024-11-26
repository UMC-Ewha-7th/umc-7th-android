package com.example.flo_clone.user.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val email: String,
    var password: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)