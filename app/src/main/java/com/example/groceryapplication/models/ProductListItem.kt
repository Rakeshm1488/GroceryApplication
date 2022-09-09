package com.example.groceryapplication.models

import java.io.Serializable

data class ProductListItem(
    val pID: Int,
    val pImage: String,
    val pName: String,
    val pPrice: Int,
    val isNew: Boolean
) : Serializable