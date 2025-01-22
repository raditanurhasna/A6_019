package com.example.projectakhir


import android.app.Application
import com.example.projectakhir.dependeciesinjection.AppContainer
import com.example.projectakhir.dependeciesinjection.AppContainerImpl


class AcaraApplications : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl()
    }
}
