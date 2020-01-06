package com.github.kongpf8848.Mu5ViewPager.demo

import android.app.Application
import android.util.Log

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            e.printStackTrace()
            Log.d("JACK9",e.message)
        }

    }
}