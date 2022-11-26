package com.example.mystore.epoxy

import coil.load
import com.example.mystore.R
import com.example.mystore.data.model.ui.UiProduct
import com.example.mystore.databinding.ModelCartItemBinding


data class CartEpoxyModel(
    val uiProduct : UiProduct,
    val onFavoriteClick : ()-> Unit,
    val onDeleteClick : () -> Unit
): ViewBindingKotlinModel<ModelCartItemBinding>(R.layout.model_cart_item) {
    override fun ModelCartItemBinding.bind() {

        productImageView.load(uiProduct.product.image)
        productTitleTextView.text = uiProduct.product.title

        val imageRes = if (uiProduct.isFavorite) R.drawable.ic_round_favorite_24 else R.drawable.ic_round_favorite_border_24
        favoriteImageView.setIconResource(imageRes)
        favoriteImageView.setOnClickListener{ onFavoriteClick() }
        deleteIconImageView.setOnClickListener { onDeleteClick() }

        viewQuantity.apply {
            tvQuantity.text = 9.toString()
            btnRemove.setOnClickListener {  }
            btnAdd.setOnClickListener {  }
        }





    }
}