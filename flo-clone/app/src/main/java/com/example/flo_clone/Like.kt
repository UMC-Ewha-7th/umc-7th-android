package com.example.flo_clone

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "LikeTable")
class Like (
    var userId : Int,
    var albumId : Int
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}