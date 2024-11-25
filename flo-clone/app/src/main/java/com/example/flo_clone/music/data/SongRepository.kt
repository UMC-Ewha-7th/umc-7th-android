package com.example.flo_clone.music.data

import android.content.Context
import android.util.Log
import com.example.flo_clone.R
import com.example.flo_clone.album.data.Album

class SongRepository(context: Context) {
    private val songDB: SongDatabase = SongDatabase.getInstance(context)!!

    fun getAllSongs(): List<Song> {
        return songDB.songDao().getAll()
    }

    fun updateSong(song: Song)  {
        songDB.songDao().update(song)
    }

    fun initPlayingAndSecond() {
        songDB.songDao().initPlayingAndSecond(false, 0)
    }

    fun getSongById(id: Int): Song {
        return songDB.songDao().getSongById(id)
    }

    fun inputDummySongs() {
        val songs: List<Song> = songDB.songDao().getAll()

        if (songs.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                "ONCE",
                "유다빈밴드",
                R.drawable.img_album_once
            )
        )

        songDB.albumDao().insert(
            Album(
                "LETTER",
                "유다빈밴드",
                R.drawable.img_album_letter
            )
        )

        songDB.albumDao().insert(
            Album(
                "항해",
                "유다빈밴드",
                R.drawable.img_album_voyage
            )
        )

        songDB.albumDao().insert(
            Album(
                "IU 5th Album 'LILAC'",
                "아이유 (IU)",
                R.drawable.img_album_lilac
            )
        )

        songDB.albumDao().insert(
            Album(
                "치얼업 (Original Soundtrack Part.5)",
                "유다빈밴드",
                R.drawable.img_album_cheerup_ost
            )
        )

        songDB.albumDao().insert(
            Album(
                "Butter (feat. Megan Thee Stallion)",
                "BTS (방탄소년단)",
                R.drawable.img_album_butter
            )
        )

        songDB.songDao().insert(
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

        songDB.songDao().insert(
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

        songDB.songDao().insert(
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

        songDB.songDao().insert(
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

        songDB.songDao().insert(
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

        songDB.songDao().insert(
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

        songDB.songDao().insert(
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

        songDB.songDao().insert(
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

        val _songs = songDB.songDao().getAll()
        Log.d("DB data", _songs.toString())

        val _albums = songDB.albumDao().getAll()
        Log.d("DB data", _albums.toString())
    }
}