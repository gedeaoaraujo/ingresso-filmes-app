package com.ingresso.filmes.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [MovieEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return synchronized(this) {
                Room.databaseBuilder(
                    context = context.applicationContext,
                    name = "movies_db",
                    klass = AppDatabase::class.java
                ).build()
            }
        }
    }
}