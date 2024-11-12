package com.ingresso.filmes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ingresso.filmes.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var dialog: AlertDialog
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        window.statusBarColor = getColor(R.color.black_gray)
        window.navigationBarColor = getColor(R.color.light_gray)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dialog = AlertDialog.Builder(this)
            .setTitle("Ocorreu um erro")
            .setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .create()

        viewModel.loading.observe(this){ loading ->
            binding.loading.changeVisibility(loading)
            binding.moviesList.changeVisibility(loading.not())
        }

        viewModel.movies.observe(this){ movies ->
            binding.moviesList.adapter = MovieListAdapter(movies)
        }

        viewModel.error.observe(this){ error ->
            if (error.isServerError) dialog.setMessage(
                getString(R.string.error_server)
            )
            else dialog.setMessage(error.message)

            if (dialog.isShowing.not()) dialog.show()
        }
    }
}

