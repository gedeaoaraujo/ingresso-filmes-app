package com.ingresso.filmes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.ingresso.filmes.databinding.MovieDetailsBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MovieDetails: Fragment() {

    private lateinit var binding: MovieDetailsBinding
    private val viewModel by activityViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieDetailsBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack()
        }

        viewModel.movies.observe(viewLifecycleOwner){ movies ->
            val movie = movies.find { it.id == viewModel.selectedMovieId }
            binding.movieName.text = movie?.title.orEmpty()
            binding.synopsisText.text = movie?.synopsis.orEmpty()
        }
    }

}