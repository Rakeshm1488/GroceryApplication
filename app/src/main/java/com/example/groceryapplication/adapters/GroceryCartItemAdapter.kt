package com.example.groceryapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.example.groceryapplication.R
import com.example.groceryapplication.databinding.CartListItemBinding
import com.example.groceryapplication.databinding.ListItemBinding
import com.example.groceryapplication.models.CartItem
import com.example.groceryapplication.models.ProductListItem
import com.example.groceryapplication.util.AppLog


class GroceryCartItemAdapter(
    deleteItemClickInterface: DeleteItemClickInterface
): RecyclerView.Adapter<CartItemViewHolder>() {

    private var itemList: List<CartItem> = listOf()

    private var itemAddInterface = deleteItemClickInterface

    var requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
        .skipMemoryCache(true)
        .signature(ObjectKey(System.currentTimeMillis()))

    fun setGroceryItems(itemList: List<CartItem>) {
        AppLog.d("Cart Adapter list: ${itemList.size}")
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = CartListItemBinding.inflate(inflater, parent, false)
        return CartItemViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holderItem: CartItemViewHolder, position: Int) {
        val item = itemList[position]
        holderItem.binding.listItem = item

        Glide
            .with(holderItem.itemView.context)
            .load(item.pImage)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
//            .apply(requestOptions)
            .into(holderItem.binding.itemImage)

        holderItem.binding.cartItemDelete.setOnClickListener {
            AppLog.d("item to update is: $item")
            itemAddInterface.onDeleteClick(item)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

class CartItemViewHolder(val binding: CartListItemBinding): RecyclerView.ViewHolder(binding.root)

interface DeleteItemClickInterface{
    fun onDeleteClick(cartItem: CartItem)
}