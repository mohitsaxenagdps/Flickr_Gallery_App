package com.example.flickrgalleryapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.flickrgalleryapp.common.Event
import com.example.flickrgalleryapp.domain.use_case.GetPhotosUseCase

class MainActivityViewModel(private val getPhotosUseCase: GetPhotosUseCase) : ViewModel() {

    private val isLoading = MutableLiveData<Event<Boolean>>()
    val _isLoading: LiveData<Event<Boolean>>
        get() = isLoading

    fun getPhotos() = liveData {
        isLoading.value = Event(true)
        val photoList = getPhotosUseCase.execute()
        emit(photoList)
        isLoading.value = Event(false)
    }

}