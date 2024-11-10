package com.ingresso.filmes.remote.responses

data class ItemsResponse(
    val items: List<MovieResponse>,
    val count: Int
)
