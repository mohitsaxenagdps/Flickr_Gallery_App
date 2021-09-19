package com.example.flickrgalleryapp.domain.use_case

import androidx.paging.PagingData
import com.example.flickrgalleryapp.data.model.PhotoX
import com.example.flickrgalleryapp.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchPhotosUseCase @Inject constructor(private val photoRepository: PhotoRepository) {

    suspend fun execute(searchText: String): Flow<PagingData<PhotoX>> =
        photoRepository.getSearchPhotos(searchText)

}