package com.example.flickrgalleryapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickrgalleryapp.data.model.PhotoX
import com.example.flickrgalleryapp.databinding.PhotoItemBinding

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    private val photoList = ArrayList<PhotoX>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount() = photoList.size

    fun submitList(photo: List<PhotoX>) {
        photoList.clear()
        photoList.addAll(photo)
        notifyDataSetChanged()
    }

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

}