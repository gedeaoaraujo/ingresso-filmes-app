package com.ingresso.filmes.remote.responses

data class PremiereDateResponse(
    val localDate: String,
    val isToday: Boolean,
    val dayOfWeek: String,
    val dayAndMonth: String,
    val hour: String,
    val year: String
)