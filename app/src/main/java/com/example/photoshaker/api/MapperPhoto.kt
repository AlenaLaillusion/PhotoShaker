package com.example.photoshaker.api

import com.example.photoshaker.data.Photo

fun mapPhoto(
    // todo Review's hint: just small mistake in word "Response" ;)
    photoResponce: PhotoResponce
) = Photo(
    id = photoResponce.id,
    urls = photoResponce.urls.small
)