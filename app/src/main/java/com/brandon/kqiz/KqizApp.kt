package com.brandon.kqiz

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KqizApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}