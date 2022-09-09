package com.example.groceryapplication.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.groceryapplication.R
import com.example.groceryapplication.adapters.DeleteItemClickInterface
import com.example.groceryapplication.adapters.GroceryCartItemAdapter
import com.example.groceryapplication.databinding.FragmentMyCartBinding
import com.example.groceryapplication.models.CartItem
import com.example.groceryapplication.util.AppLog
import com.example.groceryapplication.viewmodels.GroceryViewModel


class MyCartFragment : Fragment(), DeleteItemClickInterface {

    private lateinit var groceryCartItemsBinding: FragmentMyCartBinding
    private lateinit var groceryViewModel: GroceryViewModel
    private val groceryAdapter = GroceryCartItemAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        groceryCartItemsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_cart, container, false)
        val factory = (requireActivity() as GroceryHomeActivity).getGroceryFactory()
        groceryViewModel = ViewModelProvider(requireActivity(), factory).get(GroceryViewModel::class.java)

        groceryCartItemsBinding.cartRecyclerView.adapter = groceryAdapter

        AppLog.d("cart list: ${groceryViewModel.cartItemList.value}")
        groceryViewModel.cartItemList.value?.let {
            groceryAdapter.setGroceryItems(it)
        }

        groceryViewModel.cartItemList.observe(requireActivity()) {
            groceryAdapter.setGroceryItems(it)
        }

        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_myCartFragment_to_groceryHomeFragment)
            }
        })

        return groceryCartItemsBinding.root
    }

    override fun onDeleteClick(cartItem: CartItem) {
        groceryViewModel.deleteCartItem(cartItem)
    }
}