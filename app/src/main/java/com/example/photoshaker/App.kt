package com.example.photoshaker

import android.app.Application
import android.content.Context
import com.example.photoshaker.databinding.ActivityMainBinding

class App: Application() {
    // todo Review's hint: this is binding not used + not needed in the App class. Application hasn't UI
    private lateinit var binding: ActivityMainBinding

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context

    }
}
