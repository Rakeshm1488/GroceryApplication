package com.example.groceryapplication.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.groceryapplication.apibase.ApiService
import com.example.groceryapplication.database.ItemDAO
import com.example.groceryapplication.models.ProductListItem
import com.example.groceryapplication.models.ResultList
import com.example.groceryapplication.util.AppLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemRepository(private val apiHelper: ApiService, private val itemDB: ItemDAO) {

    private val groceryItemsData = MutableLiveData<List<ProductListItem>>()

    val itemsResults: LiveData<List<ProductListItem>>
        get() = groceryItemsData

    suspend fun getAllProducts(){
        val response = apiHelper.getProductList()
        response.enqueue(object : Callback<ResultList>{
            override fun onResponse(call: Call<ResultList>, response: Response<ResultList>) {
                if(response.body() != null) {
                    AppLog.d("API response success ${response.body()}")
                    groceryItemsData.postValue(response.body()!!.map {
                        ProductListItem(it.pID, it.pImage, it.pName, it.pPrice, true)
                    })
                } else {
                    groceryItemsData.postValue(listOf())
                }
            }

            override fun onFailure(call: Call<ResultList>, t: Throwable) {
                AppLog.d("Error occur while api call: ${t.printStackTrace()}")
                groceryItemsData.postValue(listOf())
            }
        })
    }
}