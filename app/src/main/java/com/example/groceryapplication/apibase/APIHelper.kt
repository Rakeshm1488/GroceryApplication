package com.example.groceryapplication.apibase

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIHelper {

    private const val baseURL = "https://run.mocky.io/v3/"

    fun getInstance(context: Context): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .build()
    }
}