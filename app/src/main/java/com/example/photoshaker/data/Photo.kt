package com.example.photoshaker.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
        val id: String,
        val urls: String
) : Parcelable
