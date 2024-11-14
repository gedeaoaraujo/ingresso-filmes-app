package com.ingresso.filmes

import com.ingresso.filmes.local.MovieDao
import com.ingresso.filmes.remote.IngressoService
import com.ingresso.filmes.remote.responses.MovieResponse

class IngressoRepository(
    private val service: IngressoService,
    private val movieDao: MovieDao
) {

    suspend fun loadMovies(): List<MovieResponse> {
        val response = service.getMovies()
        val items = response.body()?.items
        val list = items?.sortedBy { it.premiereDate?.localDate }
        val entities = list.orEmpty().map { it.toEntity() }
        movieDao.insertMovies(entities)
        return list.orEmpty()
    }

}