package com.ingresso.filmes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ingresso.filmes.databinding.MovieItemBinding
import com.ingresso.filmes.remote.responses.MovieResponse

class MovieListAdapter(
    private val items: List<MovieResponse>
): RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>(){

    inner class MovieViewHolder(
        private val binding: MovieItemBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(item: MovieResponse) {
            binding.title.text = item.title

            val localDate = item.premiereDate?.localDate?.toBrDate()
            binding.premiereDate.text = localDate ?: "Desconhecida"

            item.images.firstOrNull()?.also { image ->
                binding.cover.load(image.url)
            }

            binding.genre.text = item.genres.firstOrNull().orEmpty()
            binding.duration.text = buildString {
                append(item.duration)
                append("min")
            }

            binding.distributorName.text = item.distributor.ifBlank { "Desconhecido" }
            formatContentRating(item.contentRating).also { contentRating ->
                binding.contentRating.setBackgroundColor(getRatingColor(contentRating))
                binding.contentRating.text = contentRating
            }
        }

        private fun getRatingColor(contentRating: String): Int = when (contentRating) {
            "? " -> Color.GRAY
            "L " -> Color.GREEN
            else -> Color.parseColor("#FE7500")
        }

        private fun formatContentRating(contentRating: String) = Regex("(\\w+)").let {
            val matchResult = it.find(contentRating)
            when(val result = matchResult?.value.orEmpty()){
                "Verifique" -> "? "
                "Livre" -> "L "
                else -> result
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()
}