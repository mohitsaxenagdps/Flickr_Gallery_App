package com.example.flickrgalleryapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrgalleryapp.databinding.LoadingBinding
import javax.inject.Inject

class LoadingStateAdapter @Inject constructor() :
    LoadStateAdapter<LoadingStateAdapter.LoadingViewHolder>() {

    override fun onBindViewHolder(holder: LoadingViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingViewHolder {
        val binding = LoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingViewHolder(binding)
    }

    class LoadingViewHolder(private val binding: LoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
        }

    }

}