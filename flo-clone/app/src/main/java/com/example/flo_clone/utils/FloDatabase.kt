package com.example.flo_clone.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flo_clone.album.data.Album
import com.example.flo_clone.album.data.AlbumDao
import com.example.flo_clone.album.data.AlbumLike
import com.example.flo_clone.music.data.Song
import com.example.flo_clone.music.data.SongDao
import com.example.flo_clone.user.data.User
import com.example.flo_clone.user.data.UserDao

@Database(entities = [Song::class, Album::class, User::class, AlbumLike::class], version = 1)
abstract class FloDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao
    abstract fun userDao(): UserDao

    companion object {
        private var instance: FloDatabase? = null

        @Synchronized
        fun getInstance(context: Context): FloDatabase? {
            if (instance == null) {
                synchronized(FloDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FloDatabase::class.java,
                        "song-database"
                    ).allowMainThreadQueries().build()
                }
            }

            return instance
        }
    }
}