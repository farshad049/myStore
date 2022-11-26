package com.example.mystore.ui.cart

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.TypedEpoxyController
import com.example.mystore.R
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

                 data.products.forEachIndexed { index, uiProduct ->

                     VerticalSpaceEpoxyModel(8.toPx()).id("top_space_$index").addTo(this)
                     if (index != 0) DividerEpoxyModel(horizontalPadding = 16.toPx()).id("divider_$index").addTo(this)
                     VerticalSpaceEpoxyModel(8.toPx()).id("bottom_space_$index").addTo(this)

                     CartEpoxyModel(
                         uiProduct ,
                         onFavoriteClick = {
                                           viewModel.viewModelScope.launch {
                                               viewModel.store.update {
                                                   return@update viewModel.uiProductFavoriteUpdater.onProductFavorited(
                                                       productId = uiProduct.product.id ,
                                                       currentState = it
                                                   )
                                               }
                                           }
                         } ,
                         onDeleteClick = {
                             viewModel.viewModelScope.launch {
                                 viewModel.store.update {
                                     return@update viewModel.uiAddToCartUpdater.onAddToCart(
                                         productId = uiProduct.product.id ,
                                         currentState = it
                                     )
                                 }
                             }
                         }
                     ).id(uiProduct.product.id).addTo(this)
                 }

             }
        }
    }







}