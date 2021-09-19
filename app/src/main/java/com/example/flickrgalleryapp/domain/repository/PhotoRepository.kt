package com.example.flickrgalleryapp.domain.repository

import androidx.paging.PagingData
import com.example.flickrgalleryapp.data.model.PhotoX
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    suspend fun getPhotos(): Flow<PagingData<PhotoX>>
    suspend fun getSearchPhotos(searchText: String): Flow<PagingData<PhotoX>>

}