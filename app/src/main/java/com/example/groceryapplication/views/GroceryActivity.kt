package com.example.groceryapplication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.groceryapplication.R
import com.example.groceryapplication.adapters.DeleteItemClickInterface
import com.example.groceryapplication.adapters.EditItemClickInterface
import com.example.groceryapplication.adapters.GroceryItemAdapter
import com.example.groceryapplication.database.AppDatabase
import com.example.groceryapplication.databinding.ActivityGroceryItemsBinding
import com.example.groceryapplication.models.GroceryItem
import com.example.groceryapplication.repositories.ItemRepository
import com.example.groceryapplication.viewmodels.GroceryItemViewModel
import com.example.groceryapplication.viewmodels.GroceryViewModelFactory

class GroceryActivity : AppCompatActivity(), EditItemClickInterface, DeleteItemClickInterface {

    private lateinit var groceryItemsBinding: ActivityGroceryItemsBinding
    private lateinit var groceryViewModel: GroceryItemViewModel
    private val groceryAdapter = GroceryItemAdapter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        groceryItemsBinding = DataBindingUtil.setContentView(this, R.layout.activity_grocery_items)
        groceryItemsBinding.itemRecyclerView.adapter = groceryAdapter

        val repo = ItemRepository(AppDatabase.getInstance(this).itemDAO())
        val factory = GroceryViewModelFactory(repo)
        groceryViewModel = ViewModelProvider(this, factory).get(GroceryItemViewModel::class.java)
        groceryViewModel.getAllItems().observe(this, {
            groceryAdapter.setGroceryItems(it)
        })

        groceryItemsBinding.addItemFab.setOnClickListener {
            val intent = Intent(this, AddEditItemActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onEditClick(groceryItem: GroceryItem) {
        val intent = Intent(this, AddEditItemActivity::class.java)
        intent.putExtra("selectedItem", groceryItem)
        startActivity(intent)
    }

    override fun onDeleteClick(groceryItem: GroceryItem) {
        groceryViewModel.deleteItem(groceryItem)
    }
}