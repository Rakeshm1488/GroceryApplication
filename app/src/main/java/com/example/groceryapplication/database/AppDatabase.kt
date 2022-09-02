package com.example.groceryapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.groceryapplication.models.GroceryItem

@Database(entities = [GroceryItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDAO(): ItemDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return (INSTANCE ?: synchronized(AppDatabase::class) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "GroceryItemsInfo"
                ).build()
                INSTANCE = instance
                INSTANCE
            }) as AppDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}