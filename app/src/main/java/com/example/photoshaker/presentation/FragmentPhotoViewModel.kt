package com.example.photoshaker.presentation

import android.os.CountDownTimer
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

    lateinit var timer: CountDownTimer

    private val _state = MutableLiveData<State>(State.Init())
    val state: LiveData<State> get() = _state

    private val _photoData = MutableLiveData<Photo>()
    val photoData: LiveData<Photo> get() = _photoData

    private val _countTimer = MutableLiveData<Long>()
    val countTimer: LiveData<Long> get() = _countTimer

    init  {
        StartDownTimer()
    }

    fun StartDownTimer() {
        timer = object: CountDownTimer(COUNT_DOWN_TIMER, ONE_SECOND) {
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
                        Log.e(FragmentPhotoViewModel::class.java.simpleName, "Error  ${e.message}" )
                    }
                }
            }
        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
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

const val COUNT_DOWN_TIMER = 6000L
const val ONE_SECOND = 1000L
const val DONE = 0L

