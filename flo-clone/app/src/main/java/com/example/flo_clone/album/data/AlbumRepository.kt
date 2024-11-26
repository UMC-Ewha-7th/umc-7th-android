package com.example.flo_clone.album.data

import android.content.Context
import android.util.Log
import com.example.flo_clone.R
import com.example.flo_clone.common.FloDatabase

class AlbumRepository(context: Context) {
    private val albumDao = FloDatabase.getInstance(context)!!.albumDao()

    fun getAllAlbums(): List<Album> {
        return albumDao.getAll()
    }

    fun inputDummyAlbums() {
        val albums: List<Album> = albumDao.getAll()

        if (albums.isNotEmpty()) return

        albumDao.insert(
            Album(
                "ONCE",
                "유다빈밴드",
                R.drawable.img_album_once
            )
        )

        albumDao.insert(
            Album(
                "LETTER",
                "유다빈밴드",
                R.drawable.img_album_letter
            )
        )

        albumDao.insert(
            Album(
                "항해",
                "유다빈밴드",
                R.drawable.img_album_voyage
            )
        )

        albumDao.insert(
            Album(
                "IU 5th Album 'LILAC'",
                "아이유 (IU)",
                R.drawable.img_album_lilac
            )
        )

        albumDao.insert(
            Album(
                "치얼업 (Original Soundtrack Part.5)",
                "유다빈밴드",
                R.drawable.img_album_cheerup_ost
            )
        )

        albumDao.insert(
            Album(
                "Butter (feat. Megan Thee Stallion)",
                "BTS (방탄소년단)",
                R.drawable.img_album_butter
            )
        )

        val _albums = getAllAlbums()
        Log.d("DB data", _albums.toString())
    }
}