package com.example.groceryapplication.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.groceryapplication.database.ItemDAO
import com.example.groceryapplication.models.GroceryItem

class ItemRepository(private val itemDB: ItemDAO) {

    private val groceryItemsData = MutableLiveData<List<GroceryItem>>()

    val itemsResults: LiveData<List<GroceryItem>>
        get() = groceryItemsData

    fun getAllItems() = itemDB.getAllItems()

    suspend fun addItem(item: GroceryItem) = itemDB.addItem(item)

    suspend fun updateItem(item: GroceryItem) = itemDB.updateItem(item)

    suspend fun deleteItem(item: GroceryItem) = itemDB.deleteItem(item)
}