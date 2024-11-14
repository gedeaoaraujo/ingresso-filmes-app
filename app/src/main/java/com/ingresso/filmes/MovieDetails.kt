package com.ingresso.filmes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
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
            if (movie == null) return@observe

            movie.imageUrl?.also { imageUrl ->
                binding.poster.load(imageUrl)
            }

            binding.star.isSelected = movie.starred == true
            binding.star.setOnClickListener {
                if (binding.star.isSelected.not()){
                    viewModel.setStarredMovie(movie.id)
                } else {
                    viewModel.removeStarredMovie(movie.id)
                }
                binding.star.isSelected = !binding.star.isSelected
            }

            binding.share.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, movie.siteUrl)
                }
                val chooser = Intent.createChooser(intent, "Compartilhar via")
                requireContext().startActivity(chooser)
            }

            binding.movieName.text = movie.title
            binding.synopsisText.text = movie.synopsis
            binding.genre.text = buildString {
                append("Gênero\n"); append(movie.genre)
            }

            val director = movie.director.replace("\n", "")
            binding.director.text = buildString {
                append("Diretor: "); append(director)
            }

            binding.city.text = buildString {
                append("Cidade: "); append(movie.city)
            }

            binding.contentRating.text = buildString {
                append("Classificação indicativa: "); append(movie.contentRating)
            }
        }
    }

}