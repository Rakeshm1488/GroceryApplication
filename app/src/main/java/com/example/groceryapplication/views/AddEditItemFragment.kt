package com.example.groceryapplication.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.groceryapplication.R
import com.example.groceryapplication.databinding.FragmentAddEditItemBinding
import com.example.groceryapplication.models.CartItem
import com.example.groceryapplication.models.ProductListItem
import com.example.groceryapplication.util.AppLog
import com.example.groceryapplication.viewmodels.GroceryViewModel

class AddEditItemFragment : Fragment() {

    private lateinit var addEditItemBinding: FragmentAddEditItemBinding
    private lateinit var addEditViewModel: GroceryViewModel
    private var selectedItem: ProductListItem? = null
    private var cartItem: CartItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        addEditItemBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_item, container, false)
        val factory = (requireActivity() as GroceryHomeActivity).getGroceryFactory()
        addEditViewModel = ViewModelProvider(requireActivity(), factory).get(GroceryViewModel::class.java)

        val id = requireActivity().intent?.getIntExtra("selectedPID", -1)
        AppLog.d("selectedpid: $id \ncurrent list: ${addEditViewModel.itemsResults.value}")
        selectedItem = addEditViewModel.itemsResults.value?.find { it.pID == id }

        cartItem = selectedItem?.run {
            addEditViewModel.getCartItem(pID)
        }

        addEditItemBinding.groceryItem = cartItem ?: selectedItem?.let {
            cartItem = CartItem(it.pID, it.pImage, it.pName, 1, it.pPrice, it.pPrice)
            cartItem
        }

        addEditItemBinding.itemQuantityEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                addEditItemBinding.groceryItem = cartItem?.apply {
                    pQuantity = if(!s.isNullOrEmpty()) s.toString().toInt() else 1
                    pTotalPrice = pQuantity * pPrice
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        addEditItemBinding.submitButton.setOnClickListener {
            cartItem?.let { it1 ->
                val isAdded = addEditViewModel.addOrUpdateItem(it1)
                if(isAdded){
                    Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addEditItemFragment_to_myCartFragment)
                } else {
                    Toast.makeText(context, "Failed to Add", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return addEditItemBinding.root
    }
}