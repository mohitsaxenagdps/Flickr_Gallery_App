package com.example.flickrgalleryapp.presentation.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flickrgalleryapp.R
import com.example.flickrgalleryapp.databinding.ActivityMainBinding
import com.example.flickrgalleryapp.presentation.adapter.LoadingStateAdapter
import com.example.flickrgalleryapp.presentation.adapter.PhotosAdapter
import com.example.flickrgalleryapp.presentation.viewmodel.MainActivityViewModel
import com.example.flickrgalleryapp.presentation.viewmodel_factory.MainActivityViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mainActivityViewModelFactory: MainActivityViewModelFactory

    @Inject
    lateinit var adapter: PhotosAdapter
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

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.adapter =
                adapter.withLoadStateHeaderAndFooter(LoadingStateAdapter(), LoadingStateAdapter())
        }

        lifecycleScope.launchWhenStarted {
            getPhotos()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        val searchViewItem = menu?.findItem(R.id.menu_search)
        val searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    lifecycleScope.launch {
                        getSearchedPhotos(query)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                MainScope().launch {
                    delay(1500)
                    if (newText != null) {
                        getSearchedPhotos(newText)
                    }
                }
                return false
            }
        })

        searchView.setOnCloseListener {
            lifecycleScope.launch {
                getPhotos()
            }
            return@setOnCloseListener false
        }

        return super.onCreateOptionsMenu(menu)
    }

    private suspend fun getPhotos() {
        mainActivityViewModel.getPhotos().collectLatest {
            adapter.submitData(it)
        }
    }

    suspend fun getSearchedPhotos(searchText: String) {
        mainActivityViewModel.getSearchPhotos(searchText).collectLatest {
            adapter.submitData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        checkNetwork()
    }


    private fun isNetWorkAvailable(): Boolean {
        val connectivityManager =
            this@MainActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }

    private fun checkNetwork(){
        if (!isNetWorkAvailable()) {
            val snackBar = Snackbar.make(
                findViewById(android.R.id.content),
                "No Network Available",
                Snackbar.LENGTH_INDEFINITE
            )
            snackBar.setAction("Retry") {
                checkNetwork()
            }.show()
        }
        else{
            lifecycleScope.launch {
                getPhotos()
            }
        }
    }

}