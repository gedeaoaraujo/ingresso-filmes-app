package com.ingresso.filmes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ingresso.filmes.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var dialog: AlertDialog
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel by viewModel<MainViewModel>()

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

        navController = binding.navHostFragment.run {
            getFragment<NavHostFragment>().navController
        }

        dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.error_dialog_title))
            .setPositiveButton(getString(R.string.error_dialog_button)) { dialog, _ ->
                viewModel.loadMovies()
                dialog.dismiss()
            }
            .setNegativeButton("Fechar"){ dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()

        viewModel.error.observe(this){ error ->
            if (error.isServerError) dialog.setMessage(
                getString(R.string.error_server)
            )
            else dialog.setMessage(error.message)

            if (dialog.isShowing.not()) dialog.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

