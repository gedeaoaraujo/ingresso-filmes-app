package com.ingresso.filmes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingresso.filmes.remote.responses.MovieResponse
import kotlinx.coroutines.launch
import java.net.UnknownHostException

data class Error(
    val isServerError: Boolean = false,
    val message: String = ""
)

class MainViewModel(
    private val repository: IngressoRepository
): ViewModel() {

    private val _movies = MutableLiveData<List<MovieResponse>>()
    val movies: LiveData<List<MovieResponse>> = _movies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Error>()
    val error: LiveData<Error> = _error

    init {
        loadMovies()
    }

    fun loadMovies() = viewModelScope.launch {
        try {
            _loading.postValue(true)
            val movies = repository.loadMovies()
            _movies.postValue(movies)
        } catch (t: UnknownHostException){
            Log.e("Error", t.message.orEmpty())
            _error.postValue(Error(isServerError = true))
        } catch (t: Throwable){
            Log.e("Error", t.message.orEmpty())
            _error.postValue(Error(message = t.message.orEmpty()))
        } finally {
            _loading.postValue(false)
        }
    }

}

