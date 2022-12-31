package com.example.mystore.ui.explore.epoxy

import androidx.lifecycle.viewModelScope
import com.example.mystore.ui.explore.ExploreViewModel
import kotlinx.coroutines.launch

class ExploreFragmentOnClicks(private val viewModel: ExploreViewModel) {

    fun onProductSelected(productId: Int) {
        viewModel.onProductSelected(productId)
    }

    fun onQuantityChanged(productId: Int, quantity: Int) = viewModel.viewModelScope.launch {
        viewModel.store.update { currentApplicationState ->
            return@update viewModel.uiProductQuantityUpdater.onQuantityChange(
                productId = productId,
                newQuantity = quantity,
                currentState = currentApplicationState
            )
        }
    }

    fun onAddToCart(productId: Int) = viewModel.viewModelScope.launch {
        viewModel.store.update { currentApplicationState ->
            return@update viewModel.uiProductInCartUpdater.onAddToCart(
                productId = productId,
                currentState = currentApplicationState
            )
        }
    }

}