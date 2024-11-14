package com.ingresso.filmes

import androidx.lifecycle.LiveData
import com.ingresso.filmes.local.MovieDao
import com.ingresso.filmes.local.MovieEntity
import com.ingresso.filmes.remote.IngressoService

class IngressoRepository(
    private val service: IngressoService,
    private val movieDao: MovieDao
) {

    suspend fun loadMovies() {
        val response = service.getMovies()
        val items = response.body()?.items
        val entities = items.orEmpty().map { it.toEntity() }
        movieDao.insertMovies(entities)
    }

    fun listAllMovies(): LiveData<List<MovieEntity>> {
        return movieDao.listAll()
    }

    suspend fun bookmarkMovie(id: Int) {
        movieDao.bookmarkMovie(id)
    }

    suspend fun removeBookmark(id: Int) {
        movieDao.removeBookmark(id)
    }

}