package com.example.tsaving

import android.app.Application
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class BaseApplication : Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var token:String
        lateinit var custEmail:String
        lateinit var custName:String
        lateinit var accNumber:String
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this@BaseApplication

    }
}

