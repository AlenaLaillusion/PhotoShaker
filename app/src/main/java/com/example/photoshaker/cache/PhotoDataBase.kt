package com.example.photoshaker.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.photoshaker.App

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class PhotoDataBase : RoomDatabase() {

    abstract val photoDao: PhotoDao

    companion object {

        fun create(): PhotoDataBase = Room.databaseBuilder(
            App.context,
            PhotoDataBase::class.java,
            PhotoDbContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}