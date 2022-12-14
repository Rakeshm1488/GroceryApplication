package com.example.groceryapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.example.groceryapplication.R
import com.example.groceryapplication.databinding.ListItemBinding
import com.example.groceryapplication.models.ResultList
import com.example.groceryapplication.models.ProductListItem
import com.example.groceryapplication.util.AppLog


class GroceryItemAdapter(
    addItemClickInterface: AddItemClickInterface
): RecyclerView.Adapter<ItemViewHolder>() {

    private var itemList: List<ProductListItem> = listOf()

    private var itemAddInterface = addItemClickInterface

    var requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
        .skipMemoryCache(true)
        .signature(ObjectKey(System.currentTimeMillis()))

    fun setGroceryItems(itemList: List<ProductListItem>) {
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

        Glide
            .with(holderItem.itemView.context)
            .load(item.pImage)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
//            .apply(requestOptions)
            .into(holderItem.binding.itemImage)

        holderItem.binding.listItemAdd.setImageResource(
            if(item.isNew) R.drawable.ic_add_24 else R.drawable.ic_edit_24
        )

        holderItem.binding.listItemAdd.setOnClickListener {
            AppLog.d("item to update is: $item")
            itemAddInterface.onAddClick(item.pID)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

class ItemViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root)

interface AddItemClickInterface{
    fun onAddClick(pid: Int)
    fun onAddClick(productItem: ProductListItem)
}