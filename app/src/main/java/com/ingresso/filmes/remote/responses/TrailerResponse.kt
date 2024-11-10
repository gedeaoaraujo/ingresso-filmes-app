package com.ingresso.filmes.remote.responses

data class TrailerResponse(
    val type: String,
    val url: String,
    val embeddedUrl: String
)