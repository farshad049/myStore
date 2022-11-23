package com.example.mystore.ui.cart

import com.airbnb.epoxy.TypedEpoxyController
import com.example.mystore.R
import com.example.mystore.databinding.ModelCartEmptyBinding
import com.example.mystore.epoxy.CartEpoxyModel
import com.example.mystore.epoxy.DividerEpoxyModel
import com.example.mystore.epoxy.VerticalSpaceEpoxyModel
import com.example.mystore.epoxy.ViewBindingKotlinModel
import com.example.mystore.util.toPx

class CartFragmentEpoxyController
    : TypedEpoxyController<CartFragment.UiState>() {

    override fun buildModels(data: CartFragment.UiState?) {
        when (data){
            null , is CartFragment.UiState.Empty -> {
                CartEmptyEpoxyModel(onGoShoppingClick = {})
            }
             is CartFragment.UiState.NotEmpty ->{

                 data.products.forEachIndexed { index, uiProduct ->

                     VerticalSpaceEpoxyModel(8.toPx()).id("top_space_$index").addTo(this)
                     if (index != 0) DividerEpoxyModel(horizontalPadding = 16.toPx()).id("divider_$index").addTo(this)
                     VerticalSpaceEpoxyModel(8.toPx()).id("bottom_space_$index").addTo(this)

                     CartEpoxyModel(
                         uiProduct ,
                         onFavoriteClick = {} ,
                         onDeleteClick = {}
                     ).id(uiProduct.product.id).addTo(this)
                 }

             }
        }
    }







    data class CartEmptyEpoxyModel(
        val onGoShoppingClick : () -> Unit
    ):ViewBindingKotlinModel<ModelCartEmptyBinding>(R.layout.model_cart_item) {
        override fun ModelCartEmptyBinding.bind() {
            onGoShoppingClick()
        }

    }
}