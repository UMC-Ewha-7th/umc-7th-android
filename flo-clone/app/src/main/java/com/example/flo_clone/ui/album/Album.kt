package com.example.flo_clone.ui.album

import com.example.flo_clone.Song

data class Album(
    var title: String? ="",
    var Singer: String? ="",
    var coverImg: Int? = null,
    var songs: ArrayList<Song>? = null
)
