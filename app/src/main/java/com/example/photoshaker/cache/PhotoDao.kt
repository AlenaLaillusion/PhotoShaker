package com.example.photoshaker.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo_shake ORDER BY _id DESC")
    suspend fun getAllPhoto(): List<PhotoEntity>

    @Insert
    suspend fun insert(photo: PhotoEntity)

    @Query("DELETE FROM photo_shake WHERE _id IS :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM photo_shake ORDER BY _id DESC LIMIT 1")
    suspend fun getLastPhoto(): PhotoEntity
}