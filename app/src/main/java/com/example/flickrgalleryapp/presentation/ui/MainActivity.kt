package com.example.flickrgalleryapp.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flickrgalleryapp.R
import com.example.flickrgalleryapp.databinding.ActivityMainBinding
import com.example.flickrgalleryapp.presentation.viewmodel.MainActivityViewModel
import com.example.flickrgalleryapp.presentation.viewmodel_factory.MainActivityViewModelFactory
import com.example.flickrgalleryapp.presentation.adapter.PhotoAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mainActivityViewModelFactory: MainActivityViewModelFactory
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        mainActivityViewModel =
            ViewModelProvider(this, mainActivityViewModelFactory)[MainActivityViewModel::class.java]

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val response = mainActivityViewModel.getPhotos()

        val adapter = PhotoAdapter()
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.adapter = adapter
        }

        response.observe(this, {
            adapter.submitList(it)
        })

        mainActivityViewModel._isLoading.observe(this, {
            it.getContentIfNotHandled()?.let { msg ->
                if (msg) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

    }
}