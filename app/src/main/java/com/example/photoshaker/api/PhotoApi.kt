package com.example.photoshaker.api

import com.example.photoshaker.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApi {

        //https://api.unsplash.com/photos/random/?client_id=157e8743986688ec3389c04ff2d0169c9c2faf0698e59fc93b60f84c6b8763d1
        @GET("photos/random/")
        suspend fun getPhotoRandom (
            @Query("client_id") key: String = BuildConfig.API_KEY,
        ): PhotoResponce
    }