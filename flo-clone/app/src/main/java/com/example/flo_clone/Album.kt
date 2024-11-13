package com.example.flo_clone

class Album (
    var title: String? = "",
    var singer: String? ="",
    var coverImg: Int? = null,
    var songs: ArrayList<Song>? = null //수록곡 의미, 강의에서는 사용하지 않음
)