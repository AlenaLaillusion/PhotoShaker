package com.example.photoshaker.cache

import com.example.photoshaker.data.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoRepositoryImpl : PhotoRepository {

    private val photoDataBase = PhotoDataBase.create()

    override suspend fun getAllPhoto(): List<Photo> = withContext(Dispatchers.IO) {
        photoDataBase.photoDao.getAllPhoto().map { toPhoto(it) }
    }

    override suspend fun getLastPhoto(): Photo = withContext(Dispatchers.IO) {
      val photo = photoDataBase.photoDao.getLastPhoto()
        toPhoto(photo)
       }

    override suspend fun updatePhotoCache(photo: Photo): List<Photo> = withContext(Dispatchers.IO) {
        photoDataBase.photoDao.insert(toPhotoEntity(photo))
        getAllPhoto()
    }

    private fun toPhoto(entity: PhotoEntity) = Photo(
        id = entity.photoId,
        urls = entity.url
    )

    private fun toPhotoEntity(photo: Photo) = PhotoEntity(
        id = null,
        photoId = photo.id,
        url = photo.urls

    )
}