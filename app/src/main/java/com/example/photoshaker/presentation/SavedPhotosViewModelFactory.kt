package com.example.photoshaker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photoshaker.cache.PhotoRepositoryImpl
import kotlinx.serialization.ExperimentalSerializationApi

class SavedPhotosViewModelFactory: ViewModelProvider.Factory {

    @ExperimentalSerializationApi
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        FragmentSavedPhotosViewModel::class.java -> FragmentSavedPhotosViewModel(
            repositoryImpl = PhotoRepositoryImpl()
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
