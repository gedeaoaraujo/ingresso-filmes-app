package com.ingresso.filmes

import com.ingresso.filmes.remote.IngressoService
import com.ingresso.filmes.remote.responses.MovieResponse

class IngressoRepository(
    private val service: IngressoService
) {

    suspend fun loadMovies(): List<MovieResponse> {
        val response = service.getMovies()
        val items = response.body()?.items
        return items?.sortedBy { it.premiereDate?.localDate }.orEmpty()
    }

}