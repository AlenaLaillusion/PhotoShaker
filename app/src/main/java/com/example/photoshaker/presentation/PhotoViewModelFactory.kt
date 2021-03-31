package com.example.photoshaker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photoshaker.api.RetrofitModule
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.create

class PhotoViewModelFactory: ViewModelProvider.Factory{
    @ExperimentalSerializationApi
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        FragmentPhotoViewModel::class.java -> FragmentPhotoViewModel(
            photoApi = RetrofitModule.retrofit.create()
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}

