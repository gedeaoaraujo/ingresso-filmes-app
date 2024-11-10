package com.ingresso.filmes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
            binding.premiereDate.text = item.premiereDate?.localDate ?: "Desconhecida"
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