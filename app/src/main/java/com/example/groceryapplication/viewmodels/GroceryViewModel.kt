package com.example.groceryapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groceryapplication.models.CartItem
import com.example.groceryapplication.models.ProductListItem
import com.example.groceryapplication.models.ResultList
import com.example.groceryapplication.repositories.ItemRepository
import com.example.groceryapplication.util.AppLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroceryViewModel(private val itemRepository: ItemRepository): ViewModel() {

    val itemsResults: LiveData<List<ProductListItem>>
        get() = itemRepository.itemsResults

    val cartItemList = MutableLiveData<List<CartItem>>(listOf())

    fun getCartItem(pId: Int): CartItem? {
        return cartItemList.value?.find { it.pID == pId }
    }

    fun addOrUpdateItem(cartItem: CartItem): Boolean {
        var isAdded = false
        try{
            if(cartItemList.value.isNullOrEmpty() || cartItemList.value?.find { it.pID == cartItem.pID } == null) {
                cartItemList.value = cartItemList.value?.toMutableList()?.apply {
                    add(cartItem)
                    isAdded = true
                }
            } else {
                AppLog.d("adding: $cartItem")
                cartItemList.value?.find { it.pID == cartItem.pID }?.apply {
                    pName = cartItem.pName
                    pImage = cartItem.pImage
                    pQuantity = cartItem.pQuantity
                    pPrice = cartItem.pPrice
                    pTotalPrice = cartItem.pTotalPrice
                    isAdded = true
                }
                AppLog.d("cartList: $cartItemList")
            }
        } catch (e: Exception){
            isAdded = false
            e.printStackTrace()
        }

        return isAdded
    }

    fun deleteCartItem(cartItem: CartItem){
        AppLog.d("size before delete: ${cartItemList.value}")
        if(!cartItemList.value.isNullOrEmpty()) {
            cartItemList.value = cartItemList.value?.toMutableList()?.apply {
                remove(cartItem)
            }
        }
        AppLog.d("size after delete: ${cartItemList.value}")
    }


    /*fun addItem(groceryItem: GroceryItem) = viewModelScope.launch(Dispatchers.IO){
        itemRepository.addItem(groceryItem)
    }

    fun updateItem(groceryItem: GroceryItem) = viewModelScope.launch(Dispatchers.IO){
        itemRepository.updateItem(groceryItem)
    }

    fun deleteItem(groceryItem: GroceryItem) = viewModelScope.launch(Dispatchers.IO){
        itemRepository.deleteItem(groceryItem)
    }*/

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO){
            itemRepository.getAllProducts()
        }
    }
}