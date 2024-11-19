package com.example.flo_clone.ui.home

import com.example.flo_clone.model.album.Album

interface TodayAlbumAdapterListener {
    fun albumClickListener(album: Album)
    fun albumPlayClickListener(album: Album)
}