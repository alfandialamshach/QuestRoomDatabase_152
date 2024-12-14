package com.example.project7.ui.navigation

interface Alamatnavigasi {
    val route : String
}

object DestinasiHome : Alamatnavigasi {
    override val route = "home" //untuk mendefinisikan route sebagai home
}

object DestinasiDetail : Alamatnavigasi {
    override  val route = "detail" //untuk mendefinisikan route sebagai detail
    const val NIM = "nim"
    val routesWithArg = "$route/{$NIM}"
}

object DestinasiUpdate : Alamatnavigasi {
    override val route = "update" //untuk mendefinisikan route sebagai update
    const val NIM = "nim"
    val routeWithArg = "$route/{$NIM}"
}