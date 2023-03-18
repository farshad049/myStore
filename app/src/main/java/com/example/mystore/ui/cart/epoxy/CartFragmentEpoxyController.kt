package com.example.mystore.ui.cart.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.example.mystore.epoxy.DividerEpoxyModel
import com.example.mystore.epoxy.VerticalSpaceEpoxyModel
import com.example.mystore.ui.cart.CartFragment
import com.example.mystore.util.toPx

class CartFragmentEpoxyController(
 //   private val viewModel : CartFragmentViewModel,
 //   private val onGoShoppingClick : () -> Unit,
    private val onClicks : CartFragmentOnClicks
) : TypedEpoxyController<CartFragment.UiState>() {

    override fun buildModels(data: CartFragment.UiState?) {
        when (data){
            null , is CartFragment.UiState.Empty -> {
                CartEmptyEpoxyModel {
                    onClicks.onGoShoppingClick()
                }.id("empty_card").addTo(this)
            }
             is CartFragment.UiState.NotEmpty ->{

                 data.products.forEachIndexed { index, uiProductInCart ->

                     VerticalSpaceEpoxyModel(8.toPx()).id("top_space_$index").addTo(this)
                     if (index != 0) DividerEpoxyModel(horizontalMargin = 16.toPx()).id("divider_$index").addTo(this)
                     VerticalSpaceEpoxyModel(8.toPx()).id("bottom_space_$index").addTo(this)

                     CartEpoxyModel(
                         uiProductInCart ,
                         onFavoriteClick = { favoriteItemId->
                             onClicks.onFavoriteClick(favoriteItemId)
                         }  ,
                         onQuantityChangeClick = {productId, quantity ->
                             onClicks.onQuantityChanged(
                                 productId = productId,
                                 quantity = quantity
                             )
                         }
                     ).id(uiProductInCart.uiProduct.product.id).addTo(this)
                 }

             }
        }
    }


//    private fun onFavoriteClick(selectedItemId : Int){
//        viewModel.viewModelScope.launch {
//            viewModel.store.update {
//                return@update viewModel.uiProductFavoriteUpdater.onProductFavorited(
//                    productId = selectedItemId ,
//                    currentState = it
//                )
//            }
//        }
//    }


//    private fun onQuantityChangeClick(selectedItemId : Int , newQuantity : Int){
//        if (newQuantity <1) return // in order to not showing negative count
//        viewModel.viewModelScope.launch {
//            viewModel.store.update {currentState ->
//                val newMapEntry = selectedItemId to newQuantity
//                val newMap = currentState.cartQuantitiesMap + newMapEntry
//                return@update currentState.copy(cartQuantitiesMap = newMap)
//            }
//        }
//    }







}