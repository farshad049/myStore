package com.example.mystore

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyStoreApplication:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}