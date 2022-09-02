package com.example.groceryapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.groceryapplication.database.AppDatabase
import com.example.groceryapplication.databinding.ActivityAddEditItemBinding
import com.example.groceryapplication.models.GroceryItem
import com.example.groceryapplication.repositories.ItemRepository
import com.example.groceryapplication.util.AppLog
import com.example.groceryapplication.viewmodels.GroceryItemViewModel
import com.example.groceryapplication.viewmodels.GroceryViewModelFactory

class AddEditItemActivity : AppCompatActivity() {

    private lateinit var addEditItemBinding: ActivityAddEditItemBinding
    private lateinit var addEditViewModel: GroceryItemViewModel
    private var selectedItem: GroceryItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addEditItemBinding = ActivityAddEditItemBinding.inflate(layoutInflater)
        setContentView(addEditItemBinding.root)
        val repo = ItemRepository(AppDatabase.getInstance(this).itemDAO())
        val factory = GroceryViewModelFactory(repo)
        addEditViewModel = ViewModelProvider(this, factory).get(GroceryItemViewModel::class.java)

        selectedItem = intent.getSerializableExtra("selectedItem") as GroceryItem?

        selectedItem?.apply {
            addEditItemBinding.
            groceryItem = this
            AppLog.d("selected item: $this")
        }

        addEditItemBinding.submitButton.setOnClickListener {

            selectedItem = selectedItem?.apply {
                AppLog.d("Inside apply: $selectedItem")
                id = this.id
                itemName = addEditItemBinding.itemNameEdittext.text.toString()
                itemQuantity = addEditItemBinding.itemQuantityEdittext.text.toString().toInt()
                itemPrice = addEditItemBinding.itemAmountEdittext.text.toString().toFloat()
            } ?: GroceryItem(
                addEditItemBinding.itemNameEdittext.text.toString(),
                addEditItemBinding.itemQuantityEdittext.text.toString().toInt(),
                addEditItemBinding.itemAmountEdittext.text.toString().toFloat()
            )

            selectedItem?.run {
                addEditViewModel.addItem(this)
                finish()
            }

        }
    }
}