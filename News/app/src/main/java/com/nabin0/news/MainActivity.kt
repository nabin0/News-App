package com.nabin0.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nabin0.news.databinding.ActivityMainBinding
import com.nabin0.news.presentation.adapter.NewsAdapter
import com.nabin0.news.presentation.viewmodel.NewsViewModel
import com.nabin0.news.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory

    @Inject
    lateinit var newsAdapter: NewsAdapter

    lateinit var viewModel: NewsViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]

    }
}