package com.example.photoshaker.cache

import android.provider.BaseColumns

object PhotoDbContract {

    const val DATABASE_NAME = "Photo_Db"

    object Photo {
        const val TABLE_NAME = "photo_shake"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_PHOTO_ID = "photo_id"
        const val COLUMN_NAME_URL = "url"
    }

}