package com.example.photoshaker.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Urls (
    val img: String
) : Parcelable