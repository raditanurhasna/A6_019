package com.example.projectakhir


import android.app.Application
import com.example.projectakhir.dependeciesinjection.AcaraContainer
import com.example.projectakhir.dependeciesinjection.AppContainer


class AcaraApplications : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AcaraContainer()
    }
}
