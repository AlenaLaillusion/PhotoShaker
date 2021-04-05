package com.example.photoshaker.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = PhotoDbContract.Photo.TABLE_NAME,
    indices = [Index(PhotoDbContract.Photo.COLUMN_NAME_ID)]
)
data class PhotoEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PhotoDbContract.Photo.COLUMN_NAME_ID)
    val id: Long? = 0,

    @ColumnInfo(name = PhotoDbContract.Photo.COLUMN_NAME_PHOTO_ID)
    val photoId: String,

    @ColumnInfo(name = PhotoDbContract.Photo.COLUMN_NAME_URL)
    val url: String

)