package com.example.flickrgalleryapp.data.repository

import android.util.Log
import com.example.flickrgalleryapp.data.api.FlickrApi
import com.example.flickrgalleryapp.data.model.PhotoX
import com.example.flickrgalleryapp.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val flickrApi: FlickrApi) : PhotoRepository {
    override suspend fun getPhotos(): List<PhotoX> {
        return getMoviesFromApi()
    }

    private suspend fun getMoviesFromApi(): List<PhotoX> {
        var photos = emptyList<PhotoX>()
        try {
            val response = flickrApi.getPhotos()
            val body = response.body()
            if (body != null) {
                photos = body.photos.photo
            }
        } catch (e: Exception) {
            Log.i("MyTag", "Exception: ${e.message.toString()}")
        }
        return photos
    }
}