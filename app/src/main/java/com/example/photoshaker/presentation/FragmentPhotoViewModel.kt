package com.example.photoshaker.presentation

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoshaker.R
import com.example.photoshaker.api.PhotoApi
import com.example.photoshaker.api.mapPhoto
import com.example.photoshaker.cache.PhotoRepositoryImpl
import com.example.photoshaker.data.Photo
import com.example.photoshaker.domain.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentPhotoViewModel(
    private val photoApi: PhotoApi,
    // todo Review's hint: looks like that PhotoRepositoryImpl not exist in the project :(
    private val repositoryImpl: PhotoRepositoryImpl
) : ViewModel() {

    lateinit var timer: CountDownTimer

    private val _state = MutableLiveData<State>(State.Init())
    val state: LiveData<State> get() = _state

    private val _photoData = MutableLiveData<Photo>()
    val photoData: LiveData<Photo> get() = _photoData

    private val _photoLast = MutableLiveData<Photo>()
    val photoLast: LiveData<Photo> get() = _photoLast

    private val _countTimer = MutableLiveData<Long>()
    val countTimer: LiveData<Long> get() = _countTimer


    init {
        loadingLastPhoto()
    }

    // todo Review's hint: first letter is uppercase. Should be lowercase regarding kotlin style naming. Or I missed smth? :)
    fun StartDownTimer() {
        timer = object : CountDownTimer(COUNT_DOWN_TIMER, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _state.value = State.Start()
                _countTimer.value = millisUntilFinished / ONE_SECOND
            }

            override fun onFinish() {
                _state.value = State.Stop()
                _countTimer.value = DONE

                viewModelScope.launch {
                    _state.value = State.Loading()
                    try {
                        loadingPhotoApi()
                    } catch (e: Exception) {
                        _state.value = State.Error()
                        Log.e(FragmentPhotoViewModel::class.java.simpleName, "Error  ${e.message}")
                    }
                }
            }
        }
        timer.start()
    }

    fun loadingLastPhoto() {
        viewModelScope.launch {
            try {
                val lastUrl = loadLastPhoto()
                _photoLast.value = lastUrl

            } catch (e: Exception) {
                Log.e(
                    FragmentPhotoViewModel::class.java.simpleName,
                    R.string.error_photos_mesage_Db.toString() + e.message
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    suspend fun loadingPhotoApi() {
        try {
            val networkPhoto = loadingPhoto()
            _photoData.value = networkPhoto

            repositoryImpl.updatePhotoCache(networkPhoto)
        } catch (e: Exception) {
            Log.e(
                FragmentPhotoViewModel::class.java.simpleName,
                R.string.error_photos_mesage_Api.toString() + e.message
            )
        }
    }

    suspend fun loadLastPhoto(): Photo =
        withContext(Dispatchers.IO) {
            repositoryImpl.getLastPhoto()
        }

    suspend fun loadingPhoto(): Photo =
        withContext(Dispatchers.IO) {
            val photoResponce = photoApi.getPhotoRandom()
            mapPhoto(photoResponce)
        }
}

const val COUNT_DOWN_TIMER = 6000L
const val ONE_SECOND = 1000L
const val DONE = 0L

