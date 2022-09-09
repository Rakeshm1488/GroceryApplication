package com.example.groceryapplication.models

import java.io.Serializable

data class MainListItem(
    val pID: Int,
    val pImage: String,
    val pName: String,
    val pPrice: Int
) : Serializable