package com.example.flickrgalleryapp.data.model

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<PhotoX>,
    val total: Int
)