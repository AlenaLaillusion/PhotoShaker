package com.example.photoshaker.domain

sealed class State {
    // todo Review's hint: 'class' can be replaced by 'object'
    class Init : State()
    class Loading : State()
    class Error : State()
    class Start : State()
    class Stop : State()
}