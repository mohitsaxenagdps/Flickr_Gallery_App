package com.example.flickrgalleryapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickrgalleryapp.data.model.PhotoX
import com.example.flickrgalleryapp.databinding.PhotoItemBinding
import javax.inject.Inject

class PhotosAdapter @Inject constructor() : PagingDataAdapter<PhotoX, PhotosAdapter.ViewHolder>(DiffUtilCallback()) {

    inner class ViewHolder(private val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photoX: PhotoX) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(photoX.url_s)
                    .into(imageView)
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<PhotoX>() {
        override fun areItemsTheSame(oldItem: PhotoX, newItem: PhotoX): Boolean {
            return oldItem.url_s == newItem.url_s
        }

        override fun areContentsTheSame(oldItem: PhotoX, newItem: PhotoX): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = getItem(position)
        holder.bind(photo!!)
    }
}