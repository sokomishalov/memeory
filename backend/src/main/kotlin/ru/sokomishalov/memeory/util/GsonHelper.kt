package ru.sokomishalov.memeory.util


import com.google.gson.Gson
import com.google.gson.GsonBuilder

val GSON: Gson = GsonBuilder()
        .create()

val PRETTY_GSON = GsonBuilder()
        .setPrettyPrinting()
        .create()
