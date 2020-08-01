package com.example.tsaving

import android.app.Application
import android.content.Context
import com.example.tsaving.model.response.TokenResponse

class BaseApplication : Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var token:String
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this@BaseApplication
    }


}

