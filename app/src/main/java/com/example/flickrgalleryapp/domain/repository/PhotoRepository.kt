package com.example.flickrgalleryapp.domain.repository

import com.example.flickrgalleryapp.data.model.PhotoX

interface PhotoRepository {

    suspend fun getPhotos(): List<PhotoX>

}