package com.example.flickrgalleryapp.presentation.di

import com.example.flickrgalleryapp.data.repository.PhotoRepositoryImpl
import com.example.flickrgalleryapp.domain.repository.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceModule {

    @Binds
    @Singleton
    abstract fun bindingPhotoRepo(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository
}