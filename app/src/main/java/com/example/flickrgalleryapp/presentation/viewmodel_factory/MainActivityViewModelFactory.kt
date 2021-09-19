package com.example.flickrgalleryapp.presentation.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flickrgalleryapp.domain.use_case.GetPhotosUseCase
import com.example.flickrgalleryapp.domain.use_case.GetSearchPhotosUseCase
import com.example.flickrgalleryapp.presentation.viewmodel.MainActivityViewModel
import javax.inject.Inject

class MainActivityViewModelFactory @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val getSearchPhotosUseCase: GetSearchPhotosUseCase,
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(getPhotosUseCase, getSearchPhotosUseCase) as T
    }
}