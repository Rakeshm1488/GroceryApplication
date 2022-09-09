package com.example.groceryapplication.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.groceryapplication.R
import com.example.groceryapplication.adapters.AddItemClickInterface
import com.example.groceryapplication.adapters.GroceryItemAdapter
import com.example.groceryapplication.databinding.FragmentGroceryHomeBinding
import com.example.groceryapplication.models.ProductListItem
import com.example.groceryapplication.util.AppLog
import com.example.groceryapplication.viewmodels.GroceryViewModel
import com.example.groceryapplication.viewmodels.GroceryViewModelFactory

class GroceryHomeFragment : Fragment(), AddItemClickInterface {
    private lateinit var groceryItemsBinding: FragmentGroceryHomeBinding
    private lateinit var groceryViewModel: GroceryViewModel
    private val groceryAdapter = GroceryItemAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        groceryItemsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_grocery_home, container, false)
        val factory = (requireActivity() as GroceryHomeActivity).getGroceryFactory()
        groceryViewModel = ViewModelProvider(requireActivity(), factory).get(GroceryViewModel::class.java)
        groceryItemsBinding.itemRecyclerView.adapter = groceryAdapter

        AppLog.d("GHF list ${groceryViewModel.itemsResults.value}")
        groceryViewModel.itemsResults.observe(requireActivity()) {
            groceryAdapter.setGroceryItems(it)
        }

        return groceryItemsBinding.root
    }

    override fun onAddClick(pid: Int) {
        requireActivity().intent.apply {
            putExtra("selectedPID", pid)
        }
        findNavController().navigate(R.id.action_groceryHomeFragment_to_addEditItemFragment)
    }

    override fun onAddClick(productItem: ProductListItem) {

    }
}

interface GroceryFactoryInterface{
    fun getGroceryFactory(): GroceryViewModelFactory
}