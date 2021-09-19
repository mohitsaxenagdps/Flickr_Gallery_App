package com.example.flickrgalleryapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.flickrgalleryapp.data.api.FlickrApi
import com.example.flickrgalleryapp.data.model.PhotoX
import com.example.flickrgalleryapp.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val flickrApi: FlickrApi) : PhotoRepository {
    override suspend fun getPhotos(): Flow<PagingData<PhotoX>> {
        return Pager(
            config = PagingConfig(10, enablePlaceholders = false)
        ){
            PhotosPagingSource(flickrApi)
        }.flow
    }

    override suspend fun getSearchPhotos(searchText: String): Flow<PagingData<PhotoX>> {
        return Pager(
            config = PagingConfig(10, enablePlaceholders = false)
        ){
            SearchPhotosPagingSource(flickrApi, searchText)
        }.flow
    }


//    private suspend fun getPhotosFromApi(): List<PhotoX> {
//        var photos = emptyList<PhotoX>()
//        try {
//            val response = flickrApi.getPhotos()
//            val body = response.body()
//            if (body != null) {
//                photos = body.photos.photo
//            }
//        } catch (e: Exception) {
//            Log.i("MyTag", "Exception: ${e.message.toString()}")
//        }
//        return photos
//    }
//
//    private suspend fun getSearchPhotosFromApi(searchText: String): List<PhotoX> {
//        var photos = emptyList<PhotoX>()
//        try {
//            val response = flickrApi.getSearchPhotos(searchText = searchText)
//            val body = response.body()
//            if (body != null) {
//                photos = body.photos.photo
//            }
//        } catch (e: Exception) {
//            Log.i("MyTag", "Exception: ${e.message.toString()}")
//        }
//        return photos
//    }

//    fun getPhotos_1() = Pager(
//        config = PagingConfig(20, enablePlaceholders = false)
//    ){
//        PhotosPagingSource(flickrApi)
//    }.flow.cachedIn(viewModelScope)
//
//    fun getSearchPhotos(searchText: String) = Pager(
//        config = PagingConfig(10, enablePlaceholders = false)
//    ){
//        SearchPhotosPagingSource(flickrApi, searchText)
//    }.flow.cachedIn(viewModelScope)

}