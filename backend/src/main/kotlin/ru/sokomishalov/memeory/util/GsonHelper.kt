package ru.sokomishalov.memeory.util


import com.google.gson.GsonBuilder

object GsonHelper {

    val gson = GsonBuilder()
            .create()

    val prettyGson = GsonBuilder()
            .setPrettyPrinting()
            .create()
}
