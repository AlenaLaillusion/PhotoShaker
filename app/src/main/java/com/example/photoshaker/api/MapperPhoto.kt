package com.example.photoshaker.api

import com.example.photoshaker.data.Photo


    fun mapPhoto(
        photoResponce: PhotoResponce
    ) = Photo(
        id = photoResponce.id,
        urls = photoResponce.urls.small
    )