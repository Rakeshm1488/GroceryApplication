package com.example.groceryapplication.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity
data class Users(
    var itemName: String,
    var itemQuantity: Int,
    var itemPrice: Float
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "id: $id \tname: $itemName \tquantity: $itemQuantity \tprice: $itemPrice"
    }
}
