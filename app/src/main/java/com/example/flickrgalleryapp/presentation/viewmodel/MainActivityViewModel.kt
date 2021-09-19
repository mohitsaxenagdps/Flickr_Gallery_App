package com.example.flickrgalleryapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.flickrgalleryapp.data.model.PhotoX
import com.example.flickrgalleryapp.domain.use_case.GetPhotosUseCase
import com.example.flickrgalleryapp.domain.use_case.GetSearchPhotosUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class MainActivityViewModel(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val getSearchPhotosUseCase: GetSearchPhotosUseCase,
) : ViewModel() {

     suspend fun getPhotos(): Flow<PagingData<PhotoX>> {
        val data = viewModelScope.async {
            getPhotosUseCase.execute()
        }
        return data.await().cachedIn(viewModelScope)
    }

    suspend fun getSearchPhotos(searchText: String): Flow<PagingData<PhotoX>> {
        val data = viewModelScope.async {
            getSearchPhotosUseCase.execute(searchText)
        }
        return data.await().cachedIn(viewModelScope)
    }

}