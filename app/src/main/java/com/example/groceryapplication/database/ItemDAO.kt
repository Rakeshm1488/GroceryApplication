package com.example.groceryapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.groceryapplication.models.GroceryItem

@Dao
interface ItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: GroceryItem)

    @Query("Select * from GroceryItem")
    fun getAllItems(): LiveData<List<GroceryItem>>

    @Update
    fun updateItem(item: GroceryItem)

    @Delete
    fun deleteItem(item: GroceryItem)
}