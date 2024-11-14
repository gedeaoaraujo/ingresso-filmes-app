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
        val list = items?.sortedBy { it.premiereDate?.localDate }
        val entities = list.orEmpty().map { it.toEntity() }
        movieDao.insertMovies(entities)
    }

    fun listAllMovies(): LiveData<List<MovieEntity>> {
        return movieDao.listAll()
    }

}