package com.example.mystore.ui.cart

import coil.load
import com.airbnb.epoxy.TypedEpoxyController
import com.example.mystore.R
import com.example.mystore.databinding.ModelCartEmptyBinding
import com.example.mystore.databinding.ModelCartItemBinding
import com.example.mystore.epoxy.ViewBindingKotlinModel
import com.example.mystore.model.ui.UiProduct

class CartFragmentEpoxyController
    : TypedEpoxyController<CartFragment.UiState>() {

    override fun buildModels(data: CartFragment.UiState?) {
        when (data){
            null , is CartFragment.UiState.Empty -> {
                CartEmptyEpoxyModel(
                    onGoShoppingClick = {

                    }
                )
            }
             is CartFragment.UiState.NotEmpty ->{
                 data.products.forEachIndexed { index, uiProduct ->
                     CartEpoxyModel(
                         uiProduct ,
                         onFavoriteClick = {

                         } ,
                         onDeleteClick = {

                         }
                     ).id(uiProduct.product.id).addTo(this)
                 }

             }
        }
    }







    data class CartEpoxyModel(
        val uiProduct : UiProduct ,
        val onFavoriteClick : ()-> Unit ,
        val onDeleteClick : () -> Unit
    ): ViewBindingKotlinModel<ModelCartItemBinding>(R.layout.model_cart_item) {
        override fun ModelCartItemBinding.bind() {
            productImageView.load(uiProduct.product.image)
            productTitleTextView.text = uiProduct.product.title

            val imageRes = if (uiProduct.isFavorite) R.drawable.ic_round_favorite_24 else R.drawable.ic_round_favorite_border_24
            favoriteImageView.setIconResource(imageRes)
            favoriteImageView.setOnClickListener{ onFavoriteClick() }
            deleteIconImageView.setOnClickListener { onDeleteClick }
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