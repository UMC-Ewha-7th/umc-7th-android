package com.example.flo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.flo_clone.Album
import com.example.flo_clone.AlbumDao
import com.example.flo_clone.Like
import com.example.flo_clone.Song
import com.example.flo_clone.SongDao
import com.example.flo_clone.User
import com.example.flo_clone.UserDao

@Database(entities = [Song::class, User::class, Like::class, Album::class], version = 2)
abstract class SongDatabase: RoomDatabase() {
    abstract fun songDao() : SongDao
    abstract fun userDao() : UserDao
    abstract fun albumDao() : AlbumDao


    companion object{
        private var instance :SongDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SongDatabase? {
            val migration_1_2 = object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(
                        "ALTER TABLE UserTable Add COLUMN isChecked INTEGER NOT NULL DEFAULT 0"
                    )
                }

            }
            if (instance == null) {
                synchronized(SongDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDatabase::class.java,
                        "song-database"
                    ).addMigrations(migration_1_2).build()
                }
            }
            return instance
        }
    }
}
