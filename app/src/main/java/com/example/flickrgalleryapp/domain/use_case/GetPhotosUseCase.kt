package com.example.flickrgalleryapp.domain.use_case

import com.example.flickrgalleryapp.data.model.PhotoX
import com.example.flickrgalleryapp.domain.repository.PhotoRepository
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(private val photoRepository: PhotoRepository) {

    suspend fun execute(): List<PhotoX> = photoRepository.getPhotos()

}