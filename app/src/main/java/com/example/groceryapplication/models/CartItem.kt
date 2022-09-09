package com.example.groceryapplication.models

import java.io.Serializable

data class CartItem(
    var pID: Int,
    var pImage: String,
    var pName: String,
    var pQuantity: Int,
    var pPrice: Int,
    var pTotalPrice: Int
) : Serializable