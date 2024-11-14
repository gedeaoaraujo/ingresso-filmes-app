package com.ingresso.filmes

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ingresso.filmes.databinding.MovieItemBinding
import com.ingresso.filmes.local.MovieEntity

class MovieListAdapter(
    private val onMovieSelected: (String) -> Unit = {}
): RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>(){

    private var list: List<MovieEntity> = listOf()
    private var filteredList: List<MovieEntity> = listOf()

    fun submitList(items: List<MovieEntity>){
        list = items
        filteredList = items
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchText(text: String?) {
        filteredList = if (text.isNullOrBlank()) list else {
            list.filter {  it.title.contains(text, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(
        private val binding: MovieItemBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(item: MovieEntity) {
            binding.title.text = item.title
            binding.premiereDate.text = item.localDate.toBrDate()
            item.imageUrl?.also { imageUrl -> binding.cover.load(imageUrl) }

            binding.genre.text = item.genre
            binding.duration.text = buildString {
                append(item.duration)
                append("min")
            }

            binding.distributorName.text = item.distributor.ifBlank { "Desconhecido" }
            formatContentRating(item.contentRating).also { contentRating ->
                binding.contentRating.setBackgroundColor(getRatingColor(contentRating))
                binding.contentRating.text = contentRating
            }

            binding.card.run {
                setOnClickListener { onMovieSelected(item.id) }
                if (item.inPreSale) {
                    binding.presale.changeVisibility(true)
                    setCardBackgroundColor(Color.parseColor("#FE7500"))
                } else {
                    binding.presale.changeVisibility(false)
                    setCardBackgroundColor(Color.parseColor("#2A2A2A"))
                }
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
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.count()

}