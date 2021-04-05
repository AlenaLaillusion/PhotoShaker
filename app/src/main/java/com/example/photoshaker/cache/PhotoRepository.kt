package com.example.photoshaker.cache

import com.example.photoshaker.data.Photo

interface PhotoRepository {

        suspend fun getAllPhoto(): List<Photo>

        suspend fun getLastPhoto(): Photo

        suspend fun updatePhotoCache(photo: Photo): List<Photo>

}
