package com.example.mystore.epoxy

import coil.load
import com.example.mystore.R
import com.example.mystore.data.model.ui.UiProduct
import com.example.mystore.data.model.ui.UiProductInCart
import com.example.mystore.databinding.ModelCartItemBinding
import java.math.BigDecimal
import java.text.NumberFormat


data class CartEpoxyModel(
    val uiProductInCart : UiProductInCart,
    val onFavoriteClick : (Int)-> Unit,
    val onQuantityChangeClick : (Int , Int) -> Unit //first Int in Product Id ,second Int is quantity ,and i take care of both - and + functionality with same onClick
): ViewBindingKotlinModel<ModelCartItemBinding>(R.layout.model_cart_item) {
    override fun ModelCartItemBinding.bind() {

        productImageView.load(uiProductInCart.uiProduct.product.image)
        productTitleTextView.text = uiProductInCart.uiProduct.product.title

        val imageRes = if (uiProductInCart.uiProduct.isFavorite) R.drawable.ic_round_favorite_24 else R.drawable.ic_round_favorite_border_24
        favoriteImageView.setIconResource(imageRes)
        favoriteImageView.setOnClickListener{ onFavoriteClick(uiProductInCart.uiProduct.product.id) }

        viewQuantity.apply {
            tvQuantity.text = uiProductInCart.quantity.toString()
            btnRemove.setOnClickListener { onQuantityChangeClick(uiProductInCart.uiProduct.product.id,uiProductInCart.quantity -1) }
            btnAdd.setOnClickListener { onQuantityChangeClick(uiProductInCart.uiProduct.product.id,uiProductInCart.quantity +1) }
        }

        val currencyFormatter = NumberFormat.getCurrencyInstance()
        tvTotalPrice.text= currencyFormatter.format(uiProductInCart.uiProduct.product.price * BigDecimal(uiProductInCart.quantity))





    }
}