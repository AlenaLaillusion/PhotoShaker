package com.example.photoshaker.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoshaker.api.PhotoApi
import com.example.photoshaker.api.mapPhoto
import com.example.photoshaker.data.Photo
import com.example.photoshaker.domain.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentPhotoViewModel(private val photoApi: PhotoApi) : ViewModel() {

    private val _state = MutableLiveData<State>(
        State.Init()
    )
    val state: LiveData<State> get() = _state

    private val _photoData = MutableLiveData<Photo>()
    val photoData: LiveData<Photo> get() = _photoData

    init  {
        viewModelScope.launch {
            _state.value = State.Loading()
            try {
                loadingPhotoApi()
            } catch (e: Exception) {
                Log.e(FragmentPhotoViewModel::class.java.simpleName, "Error  ${e.message}" )
            }
        }
    }

    suspend fun loadingPhotoApi() {
        val networkPhoto = loadingPhoto()
        _photoData.value = networkPhoto
    }

     suspend fun loadingPhoto(): Photo =
        withContext(Dispatchers.IO) {
            val photoResponce = photoApi.getPhotoRandom()
            mapPhoto(photoResponce)
        }
}