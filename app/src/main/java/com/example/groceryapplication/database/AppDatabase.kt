package com.example.groceryapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.groceryapplication.models.Users

@Database(entities = [Users::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDAO(): ItemDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "GroceryItemsInfo"
                    ).build()
                }
            }

            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}