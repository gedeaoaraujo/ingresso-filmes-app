package com.ingresso.filmes

import android.view.View
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun String.toBrDate(): String {
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    return OffsetDateTime.parse(this, formatter)
        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        .toString()
}

fun View.changeVisibility(visible: Boolean){
    visibility = if (visible) View.VISIBLE else View.GONE
}