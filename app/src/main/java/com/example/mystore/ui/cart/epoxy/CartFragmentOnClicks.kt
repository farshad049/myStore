package com.example.mystore.ui.cart.epoxy

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.example.mystore.R
import com.example.mystore.ui.MainActivity
import com.example.mystore.ui.cart.CartFragmentViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartFragmentOnClicks @Inject constructor(
    private val viewModel: CartFragmentViewModel ,
    private val activity: Activity
    ) {

    fun onQuantityChanged(productId: Int, quantity: Int) = viewModel.viewModelScope.launch {
        viewModel.store.update { currentApplicationState ->
            return@update viewModel.uiProductQuantityUpdater.onQuantityChange(
                productId = productId,
                newQuantity = quantity,
                currentState = currentApplicationState
            )
        }
    }

    fun onFavoriteClick(selectedItemId : Int){
        viewModel.viewModelScope.launch {
            viewModel.store.update {
                return@update viewModel.uiProductFavoriteUpdater.onProductFavorited(
                    productId = selectedItemId ,
                    currentState = it
                )
            }
        }
    }

    fun onGoShoppingClick(){
        (activity as? MainActivity)?.navigateToTab(R.id.productListFragment)
    }

}