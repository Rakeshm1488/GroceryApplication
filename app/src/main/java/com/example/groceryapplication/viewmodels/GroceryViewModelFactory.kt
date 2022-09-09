package com.example.groceryapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.groceryapplication.repositories.ItemRepository

class GroceryViewModelFactory(private val itemRepository: ItemRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GroceryViewModel(itemRepository) as T
    }
}