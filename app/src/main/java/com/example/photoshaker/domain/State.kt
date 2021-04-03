package com.example.photoshaker.domain

sealed class State {
    class Init : State()
    class Loading : State()
    class Error : State()
    class Start : State()
    class Stop : State()
}