package com.ingresso.filmes.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movie ORDER BY localDate")
    fun listAll(): LiveData<List<MovieEntity>>

    @Query("UPDATE movie SET starred = 1 WHERE movie.id = :id")
    suspend fun bookmarkMovie(id: String)

    @Query("UPDATE movie SET starred = 0 WHERE movie.id = :id")
    suspend fun removeBookmark(id: String)

}