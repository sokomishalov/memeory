package ru.sokomishalov.memeory.util


import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonHelper {

    val gson = Gson()
    val prettyGson = GsonBuilder()
            .setPrettyPrinting()
            .create()
}
