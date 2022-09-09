package com.example.groceryapplication.apibase

import com.example.groceryapplication.models.ResultList
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("a3075f1c-18db-49f3-9225-340b6b63593a")
    fun getProductList(): Call<ResultList>
}