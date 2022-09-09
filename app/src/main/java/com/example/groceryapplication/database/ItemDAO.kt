package com.example.groceryapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.groceryapplication.models.Users

@Dao
interface ItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: Users)

    @Query("Select * from Users")
    fun getAllItems(): LiveData<List<Users>>

    @Update
    fun updateItem(item: Users)

    @Delete
    fun deleteItem(item: Users)
}