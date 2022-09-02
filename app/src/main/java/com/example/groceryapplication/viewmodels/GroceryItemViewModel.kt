package com.example.groceryapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groceryapplication.models.GroceryItem
import com.example.groceryapplication.repositories.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroceryItemViewModel(private val itemRepository: ItemRepository): ViewModel() {

    fun addItem(groceryItem: GroceryItem) = viewModelScope.launch(Dispatchers.IO){
        itemRepository.addItem(groceryItem)
    }

    fun updateItem(groceryItem: GroceryItem) = viewModelScope.launch(Dispatchers.IO){
        itemRepository.updateItem(groceryItem)
    }

    fun deleteItem(groceryItem: GroceryItem) = viewModelScope.launch(Dispatchers.IO){
        itemRepository.deleteItem(groceryItem)
    }

    fun getAllItems() = itemRepository.getAllItems()
}