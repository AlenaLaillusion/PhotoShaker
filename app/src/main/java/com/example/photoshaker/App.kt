package com.example.photoshaker

import android.app.Application
import android.content.Context
import com.example.photoshaker.databinding.ActivityMainBinding

class App: Application() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context

    }
}
