package com.ingresso.filmes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ingresso.filmes.databinding.MoviesListBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MoviesList: Fragment() {

    private lateinit var binding: MoviesListBinding
    private val viewModel by activityViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesListBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner){}

        viewModel.loading.observe(viewLifecycleOwner){ loading ->
            binding.loading.changeVisibility(loading)
            binding.moviesList.changeVisibility(loading.not())
            binding.swipe.isRefreshing = loading
        }

        val movieListAdapter = MovieListAdapter { id ->
            viewModel.setSelectedMovie(id)
            findNavController().navigate(R.id.secondFragment)
        }

        binding.moviesList.adapter = movieListAdapter
        viewModel.movies.observe(viewLifecycleOwner){ movies ->
            movieListAdapter.submitList(movies)
        }

        binding.search.setOnClickListener {
            binding.searchText.run { changeVisibility(!isVisible) }
        }

        binding.searchText.doOnTextChanged { text, _, _, _ ->
            movieListAdapter.searchText(text.toString())
        }

        binding.swipe.setOnRefreshListener {
            viewModel.loadMovies()
        }
    }
}