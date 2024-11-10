package com.ingresso.filmes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingresso.filmes.remote.responses.MovieResponse
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: IngressoRepository = IngressoRepository()
): ViewModel() {

    private val _movies = MutableLiveData<List<MovieResponse>>()
    val movies: LiveData<List<MovieResponse>> = _movies

    init {
        loadMovies()
    }

    private fun loadMovies() = viewModelScope.launch {
        try {
            val movies = repository.loadMovies()
            _movies.postValue(movies)
        } catch (t: Throwable){
            Log.e("Error", t.message.orEmpty())
        }
    }

}
