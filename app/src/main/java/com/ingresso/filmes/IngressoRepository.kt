package com.ingresso.filmes

import com.ingresso.filmes.remote.IngressoClient
import com.ingresso.filmes.remote.IngressoService
import com.ingresso.filmes.remote.responses.MovieResponse

class IngressoRepository(
    private val service: IngressoService = IngressoClient.ingressoService
) {

    suspend fun loadMovies(): List<MovieResponse> {
        val response = service.getMovies()
        if (response.isSuccessful.not()) {
            throw Throwable(response.message().orEmpty())
        }
        return response.body()?.items.orEmpty()
    }

}