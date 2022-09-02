package com.example.groceryapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapplication.databinding.ListItemBinding
import com.example.groceryapplication.models.GroceryItem
import com.example.groceryapplication.util.AppLog

class GroceryItemAdapter(
    editItemClickInterface: EditItemClickInterface,
    deleteItemClickInterface: DeleteItemClickInterface
): RecyclerView.Adapter<ItemViewHolder>() {

    private var itemList = listOf<GroceryItem>()

    private var editInterface = editItemClickInterface
    private var deleteInterface = deleteItemClickInterface

    fun setGroceryItems(itemList: List<GroceryItem>) {
        AppLog.d("Adapter list: ${itemList.size}")
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = ListItemBinding.inflate(inflater, parent, false)
        return ItemViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holderItem: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holderItem.binding.listItem = item

        holderItem.binding.listItemUpdate.setOnClickListener {
            AppLog.d("item to update is: ${item.toString()}")
            editInterface.onEditClick(item)
        }

        holderItem.binding.listItemDelete.setOnClickListener {
            AppLog.d("item to delete is: ${item.toString()}")
            deleteInterface.onDeleteClick(item)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

class ItemViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root)

interface EditItemClickInterface{
    fun onEditClick(groceryItem: GroceryItem)
}
interface DeleteItemClickInterface{
    fun onDeleteClick(groceryItem: GroceryItem)
}