package com.ingresso.filmes.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val contentRating: String,
    val duration: String,
    val rating: Int,
    val synopsis: String,
    val director: String,
    val distributor: String,
    val inPreSale: Boolean,
    val localDate: String,
    val city: String,
    val imageUrl: String?,
    val genre: String
)
