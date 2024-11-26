package com.example.flo_clone.album.data

import android.content.Context
import android.util.Log
import com.example.flo_clone.R
import com.example.flo_clone.common.FloDatabase

class AlbumRepository(context: Context) {
    private val albumDao = FloDatabase.getInstance(context)!!.albumDao()

    fun getAlbumById(id: Int): Album {
        return albumDao.getAlbumById(id)
    }

    fun getAllAlbums(): List<Album> {
        return albumDao.getAllAlbums()
    }

    fun getLikedAlbums(userId: Int): List<Album> {
        return albumDao.getLikedAlbums(userId)
    }

    fun isLikedAlbum(userId: Int, albumId: Int): Boolean {
        return albumDao.isLikedAlbum(userId, albumId)
    }

    fun insertLikeAlbum(userId: Int, albumId: Int) {
        albumDao.insertLikeAlbum(AlbumLike(userId, albumId))
    }

    fun deleteLikeAlbum(userId: Int, albumId: Int) {
        albumDao.deleteLikeAlbum(userId, albumId)
    }

    fun inputDummyAlbums() {
        val albums: List<Album> = albumDao.getAllAlbums()

        if (albums.isNotEmpty()) return

        albumDao.insertAlbum(
            Album(
                "ONCE",
                "유다빈밴드",
                R.drawable.img_album_once
            )
        )

        albumDao.insertAlbum(
            Album(
                "LETTER",
                "유다빈밴드",
                R.drawable.img_album_letter
            )
        )

        albumDao.insertAlbum(
            Album(
                "항해",
                "유다빈밴드",
                R.drawable.img_album_voyage
            )
        )

        albumDao.insertAlbum(
            Album(
                "IU 5th Album 'LILAC'",
                "아이유 (IU)",
                R.drawable.img_album_lilac
            )
        )

        albumDao.insertAlbum(
            Album(
                "치얼업 (Original Soundtrack Part.5)",
                "유다빈밴드",
                R.drawable.img_album_cheerup_ost
            )
        )

        albumDao.insertAlbum(
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