package com.example.project7

import android.app.Application
import com.example.project7.dependeciesinjection.ContainerApp
import com.example.project7.dependeciesinjection.InterfaceContainerApp

class KrsApp : Application() {
    lateinit var containerApp: ContainerApp // fungsinya untuk menyimpan

    override fun onCreate(){
        super.onCreate()
        containerApp = ContainerApp(this) //
        //instance adalah object yang dibuat dari class
    }
}