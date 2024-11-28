package com.example.flo_clone.music.data

import android.content.Context
import android.util.Log
import com.example.flo_clone.R
import com.example.flo_clone.utils.FloDatabase

class SongRepository(context: Context) {
    private val songDao: SongDao = FloDatabase.getInstance(context)!!.songDao()

    fun getAllSongs(): List<Song> {
        return songDao.getAll()
    }

    fun updateSong(song: Song) {
        songDao.update(song)
    }

    fun initPlayingAndSecond() {
        songDao.initPlayingAndSecond(false, 0)
    }

    fun getSongById(id: Int): Song {
        return songDao.getSongById(id)
    }

    fun getSongsByAlbumId(albumId: Int): List<Song> {
        return songDao.getSongsByAlbumId(albumId)
    }

    fun updateLikeById(id: Int, isLike: Boolean) {
        songDao.updateLikeById(id, isLike)
    }

    fun getLikedSongs(isLike: Boolean): List<Song> {
        return songDao.getLikedSongs(isLike)
    }

    fun inputDummySongs() {
        val songs: List<Song> = songDao.getAll()

        if (songs.isNotEmpty()) return

        songDao.insert(
            Song(
                "Calling",
                "유다빈밴드",
                0,
                194,
                false,
                "music_calling",
                R.drawable.img_album_once,
                false,
                1
            )
        )

        songDao.insert(
            Song(
                "ONCE",
                "유다빈밴드",
                0,
                216,
                false,
                "music_once",
                R.drawable.img_album_once,
                false,
                1
            )
        )

        songDao.insert(
            Song(
                "땅",
                "유다빈밴드",
                0,
                270,
                false,
                "music_ground",
                R.drawable.img_album_letter,
                false,
                2
            )
        )

        songDao.insert(
            Song(
                "LETTER",
                "유다빈밴드",
                0,
                207,
                false,
                "music_letter",
                R.drawable.img_album_letter,
                false,
                2
            )
        )

        songDao.insert(
            Song(
                "항해",
                "유다빈밴드",
                0,
                226,
                false,
                "music_voyage",
                R.drawable.img_album_voyage,
                false,
                3
            )
        )

        songDao.insert(
            Song(
                "LILAC (라일락)",
                "아이유 (IU)",
                0,
                217,
                false,
                "music_lilac",
                R.drawable.img_album_lilac,
                false,
                4
            )
        )

        songDao.insert(
            Song(
                "오늘이야 (Good Day)",
                "유다빈밴드",
                0,
                231,
                false,
                "music_good_day",
                R.drawable.img_album_cheerup_ost,
                false,
                5
            )
        )

        songDao.insert(
            Song(
                "오늘이야 (Good Day) (Inst.ver)",
                "유다빈밴드",
                0,
                231,
                false,
                "music_good_day_inst",
                R.drawable.img_album_cheerup_ost,
                false,
                5
            )
        )

        val _songs = getAllSongs()
        Log.d("DB data", _songs.toString())
    }
}