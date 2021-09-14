package com.example.flickrgalleryapp.data.api

import com.example.flickrgalleryapp.common.Constants.API_KEY
import com.example.flickrgalleryapp.common.Constants.METHOD
import com.example.flickrgalleryapp.data.model.Photo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest/")
    suspend fun getPhotos(
        @Query("method") method: String = METHOD,
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallBack: Int = 1,
        @Query("extras") extras: String = "url_s"
    ): Response<Photo>

}