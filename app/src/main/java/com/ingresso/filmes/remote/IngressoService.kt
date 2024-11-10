package com.ingresso.filmes.remote

import com.ingresso.filmes.remote.responses.ItemsResponse
import retrofit2.Response
import retrofit2.http.GET

interface IngressoService {

    @GET("events/coming-soon/partnership/desafio")
    suspend fun getMovies(): Response<ItemsResponse>
}