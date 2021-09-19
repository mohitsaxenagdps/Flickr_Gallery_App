package com.example.flickrgalleryapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.flickrgalleryapp.data.api.FlickrApi
import com.example.flickrgalleryapp.data.model.PhotoX

class PhotosPagingSource(private val flickrApi: FlickrApi) : PagingSource<Int, PhotoX>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoX> {
        val page = params.key ?: 1
        return try {
            val response = flickrApi.getPhotos(page = page).body()!!.photos.photo
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (response.isEmpty()) null else page + 1
            LoadResult.Page(
                response,
                prevKey,
                nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoX>): Int? {
        TODO("Not yet implemented")
    }
}