package com.ingresso.filmes

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

            movie?.imageUrl?.also { imageUrl ->
                binding.poster.load(imageUrl)
            }

            binding.star.isSelected = movie?.starred == true
            binding.star.setOnClickListener {
                if (binding.star.isSelected.not()){
                    viewModel.setStarredMovie(movie?.id)
                } else {
                    viewModel.removeStarredMovie(movie?.id)
                }
                binding.star.isSelected = !binding.star.isSelected
            }

            binding.movieName.text = movie?.title.orEmpty()
            binding.synopsisText.text = movie?.synopsis.orEmpty()
            binding.genre.text = "Gênero\n${movie?.genre ?: "?"}"
            binding.rating.text = "Nota\n${movie?.rating?.toFloat() ?: 0.0}/10"

            val director = movie?.director?.replace("\n", "")
            binding.director.text = "Diretor: ${director ?: "Desconhecido"}"

            binding.city.text = "Cidade: ${movie?.city ?: "Desconhecida"}"
            binding.contentRating.text = "Classificação indicativa: ${movie?.contentRating ?: "?"}"
        }
    }

}