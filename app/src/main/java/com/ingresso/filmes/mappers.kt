package com.ingresso.filmes

import com.ingresso.filmes.local.MovieEntity
import com.ingresso.filmes.remote.responses.MovieResponse

fun MovieResponse.toEntity(): MovieEntity {
    return MovieEntity(
        apiId = id,
        title = title,
        contentRating = contentRating,
        duration = duration,
        rating = rating,
        synopsis = synopsis,
        director = director,
        distributor = distributor,
        inPreSale = inPreSale,
        localDate = premiereDate?.localDate.orEmpty(),
        city = city,
        imageUrl = images.map { it.url }.firstOrNull(),
        genre = genres.first()
    )
}