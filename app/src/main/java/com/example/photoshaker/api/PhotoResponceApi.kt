package com.example.photoshaker.api

import kotlinx.serialization.Serializable

@Serializable
data class PhotoResponce (
    val id: String,
    val urls: UrlsResponce
)

@Serializable
data class UrlsResponce(
    val full: String,
    val small: String
)
