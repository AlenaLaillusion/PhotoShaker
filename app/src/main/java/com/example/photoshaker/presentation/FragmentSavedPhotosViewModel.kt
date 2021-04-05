package com.example.photoshaker.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoshaker.R
import com.example.photoshaker.cache.PhotoRepositoryImpl
import com.example.photoshaker.data.Photo
import kotlinx.coroutines.launch

class FragmentSavedPhotosViewModel(
    private val repositoryImpl: PhotoRepositoryImpl
): ViewModel() {

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> get() = _photos

    init {
        loadPhotos()
    }

    fun loadPhotos() {
        viewModelScope.launch {
            loadingPhotosDb()
        }
    }

    private suspend fun loadingPhotosDb() {
        try {
            val localPhotos = repositoryImpl.getAllPhoto()

            if (localPhotos.isNotEmpty()) {
                _photos.value = localPhotos
            }
        } catch (e: Exception) {
            Log.e(FragmentSavedPhotosViewModel::class.java.simpleName,
                R.string.error_photos_mesage_Db.toString() + e.message)
        }
    }
}