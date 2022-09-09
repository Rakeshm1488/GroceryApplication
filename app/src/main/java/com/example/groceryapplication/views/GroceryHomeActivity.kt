package com.example.groceryapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.groceryapplication.R
import com.example.groceryapplication.apibase.APIHelper
import com.example.groceryapplication.apibase.ApiService
import com.example.groceryapplication.database.AppDatabase
import com.example.groceryapplication.repositories.ItemRepository
import com.example.groceryapplication.util.AppLog
import com.example.groceryapplication.viewmodels.GroceryViewModel
import com.example.groceryapplication.viewmodels.GroceryViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroceryHomeActivity : AppCompatActivity(), GroceryFactoryInterface {

    private lateinit var groceryViewModel: GroceryViewModel
    private lateinit var groceryViewModelFactory: GroceryViewModelFactory
    private var isReady = false

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grocery_home)
        setupPreDrawListener()

        val apiHelper = APIHelper.getInstance(this).create(ApiService::class.java)
        val dbDao = AppDatabase.getInstance(this).itemDAO()
        val repo = ItemRepository(apiHelper, dbDao)
        groceryViewModelFactory = GroceryViewModelFactory(repo)
        groceryViewModel = ViewModelProvider(this, groceryViewModelFactory).get(GroceryViewModel::class.java)

        initializeData()

        groceryViewModel.itemsResults.observe(this) {
            it?.let { it2 ->
                isReady = it2.isNotEmpty()
            }
        }

    }

    private fun initializeData() {
        lifecycleScope.launch(Dispatchers.IO){
            groceryViewModel.getAllProducts()
        }
    }

    override fun getGroceryFactory(): GroceryViewModelFactory {
        return groceryViewModelFactory
    }

    /**
     * Use this method only if you want to keep splash screen for longer than cold start time.
     * Manage `isReady` flag according to your logic.
     */
    private fun setupPreDrawListener() {
        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.
                    return if (isReady) {
                        // The content is ready; start drawing.
                        AppLog.d("Content is Ready")
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready; suspend.
                        AppLog.d("Still Loading...")
                        false
                    }
                }
            }
        )
    }
}