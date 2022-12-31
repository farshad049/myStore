package com.example.mystore.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.airbnb.epoxy.Carousel
import com.example.mystore.R
import com.example.mystore.ui.productList.ProductViewModel
import com.example.mystore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding



    private val viewModel: ProductViewModel by lazy {
        ViewModelProvider(this)[ProductViewModel::class.java]
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appBarConfiguration=AppBarConfiguration(
        topLevelDestinationIds = setOf(
            R.id.productListFragment,
            R.id.profileFragment,
            R.id.cartFragment,
            R.id.exploreFragment
        )
        )

        //enable the nav controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navHostController = navHostFragment.navController

        //set up fragment title in toolbar
        setupActionBarWithNavController(navHostController,appBarConfiguration)

        // Setup bottom nav bar
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navHostController)

        //to prevent snapping in carousel
        Carousel.setDefaultGlobalSnapHelperFactory(null)


        //set count of in cart product in bottom navigation badge
        viewModel.store.stateFlow.map { it.inCartProductIds.size }.distinctUntilChanged().asLiveData().observe(this){
            binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment).apply {
                number = it
                isVisible = it > 0  //if count of products is greater than 0 show them
            }

        }
















    }//FUN

    //i use this function to send user to every bottom navigation item i want
    fun navigateToTab(@IdRes destinationId : Int){
        binding.bottomNavigationView.selectedItemId = destinationId
    }





}