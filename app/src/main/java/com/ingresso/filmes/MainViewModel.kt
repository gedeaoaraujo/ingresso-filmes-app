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

    val movies = repository.listAllMovies()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Error>()
    val error: LiveData<Error> = _error

    var selectedMovieId: String = "0"
        private set

    val starredMovies = mutableSetOf("")

    init {
        loadMovies()
    }

    fun loadMovies() = viewModelScope.launch {
        try {
            _loading.postValue(true)
            repository.loadMovies()
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

    fun setSelectedMovie(id: String) {
        selectedMovieId = id
    }

    fun setStarredMovie(id: String?) {
        if (id.orEmpty().isBlank()) return
        starredMovies.add(id!!)
    }

    fun removeStarredMovie(id: String?) {
        if (id.orEmpty().isBlank()) return
        starredMovies.remove(id!!)
    }

}

