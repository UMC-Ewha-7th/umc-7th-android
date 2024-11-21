package com.example.week7

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Song::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    // DAO 정의
    abstract fun songDao(): SongDao?

    companion object {
        // Singleton Instance
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "music-app.db" // 데이터베이스 이름
                ).build()
            }
            return instance
        }
    }
}
