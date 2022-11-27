package com.example.mystore.ui.cart

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.TypedEpoxyController
import com.example.mystore.R
import com.example.mystore.data.model.ui.UiProductInCart
import com.example.mystore.databinding.ModelCartEmptyBinding
import com.example.mystore.epoxy.*
import com.example.mystore.util.toPx
import kotlinx.coroutines.launch

class CartFragmentEpoxyController(
    private val viewModel : CartFragmentViewModel ,
    private val onGoShoppingClick : () -> Unit
) : TypedEpoxyController<CartFragment.UiState>() {

    override fun buildModels(data: CartFragment.UiState?) {
        when (data){
            null , is CartFragment.UiState.Empty -> {
                CartEmptyEpoxyModel(onGoShoppingClick).id("empty_card").addTo(this)
            }
             is CartFragment.UiState.NotEmpty ->{

                 data.products.forEachIndexed { index, uiProductInCart ->

                     VerticalSpaceEpoxyModel(8.toPx()).id("top_space_$index").addTo(this)
                     if (index != 0) DividerEpoxyModel(horizontalPadding = 16.toPx()).id("divider_$index").addTo(this)
                     VerticalSpaceEpoxyModel(8.toPx()).id("bottom_space_$index").addTo(this)

                     CartEpoxyModel(
                         uiProductInCart ,
                         onFavoriteClick = ::onFavoriteClick ,
                         onDeleteClick = ::onDeleteClick,
                         onQuantityChangeClick = ::onQuantityChangeClick
                     ).id(uiProductInCart.uiProduct.product.id).addTo(this)
                 }

             }
        }
    }


    private fun onFavoriteClick(selectedItemId : Int){
        viewModel.viewModelScope.launch {
            viewModel.store.update {
                return@update viewModel.uiProductFavoriteUpdater.onProductFavorited(
                    productId = selectedItemId ,
                    currentState = it
                )
            }
        }
    }

    private fun onDeleteClick(selectedItemId : Int){
        viewModel.viewModelScope.launch {
            viewModel.store.update {
                return@update viewModel.uiAddToCartUpdater.onAddToCart(
                    productId = selectedItemId ,
                    currentState = it
                )
            }
        }
    }

    private fun onQuantityChangeClick(selectedItemId : Int , newQuantity : Int){
        if (newQuantity <1) return // in order to not showing negative count
        viewModel.viewModelScope.launch {
            viewModel.store.update {currentState ->
                val newMapEntry = selectedItemId to newQuantity
                val newMap = currentState.cartQuantitiesMap + newMapEntry
                return@update currentState.copy(cartQuantitiesMap = newMap)
            }
        }
    }







}