package com.ingresso.filmes.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie",
    indices = [Index(value = ["apiId"], unique = true)]
)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val apiId: String,
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
    val genre: String,
    val starred: Boolean = false
)
