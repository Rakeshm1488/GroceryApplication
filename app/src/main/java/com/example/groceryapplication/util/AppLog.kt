package com.example.groceryapplication.util

import android.util.Log

object AppLog {

    private const val TAG: String = "GroceryApp"

    fun i(str: String){
        Log.i(TAG, str)
    }

    fun d(str: String){
        Log.d(TAG, str)
    }

    fun e(str: String){
        Log.e(TAG, str)
    }
}