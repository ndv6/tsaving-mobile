package com.example.tsaving

import android.app.Application
import android.content.Context

class BaseApplication : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this@BaseApplication
    }
}