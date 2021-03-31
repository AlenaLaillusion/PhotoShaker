package com.example.photoshaker.api

import com.example.photoshaker.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class PhotoApiHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val request = originalRequest.newBuilder()
            .url(originalHttpUrl)
            .addHeader(
                API_KEY_HEADER,
                BuildConfig.API_KEY
            )
            .build()

        return chain.proceed(request)
    }

    companion object {
        private const val API_KEY_HEADER = "api-key"
    }
}