package com.example.mystore.epoxy

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import com.example.mystore.R
import com.example.mystore.databinding.ModelProductItemBinding
import com.example.mystore.data.model.ui.UiProduct
import java.text.NumberFormat
import kotlin.math.roundToInt

data class ProductEpoxyModel(
    val uiProduct: UiProduct?,
    val onFavoriteClick : (Int) -> Unit,
    val onExpandClick : (Int) -> Unit,
    val onAddToCartClick : (Int) -> Unit
) :ViewBindingKotlinModel<ModelProductItemBinding>(R.layout.model_product_item) {
    override fun ModelProductItemBinding.bind() {
        shimmerLayout.isVisible = uiProduct == null // if product is null then set shimmerLayout to be visible
        cardProduct.isInvisible = uiProduct == null

        uiProduct?.let { combinedProduct->
            shimmerLayout.stopShimmer()

            tvTitle.text=combinedProduct.product.title
            tvCategory.text=combinedProduct.product.category
            tvDescription.text=combinedProduct.product.description

            val currencyFormatter = NumberFormat.getCurrencyInstance()
            tvPrice.text= currencyFormatter.format(combinedProduct.product.price)

            progressImage.isVisible=true
            ivProduct.load(combinedProduct.product.image){
                listener { request, result ->
                    progressImage.isGone=true
                }
            }

            val imageRes=if (combinedProduct.isFavorite) {
                R.drawable.ic_round_favorite_24
            } else {
                R.drawable.ic_round_favorite_border_24
            }

            tvRating.text = "${uiProduct.product.rating.rate}"
            indicatorRating.progress = ((uiProduct.product.rating.rate)*10).roundToInt()

            //handling text box below the products
            tvDescription.isVisible=combinedProduct.isExpanded
            root.setOnClickListener { onExpandClick(combinedProduct.product.id) }

            btnFavorite.setIconResource(imageRes )

            //handling click on favorite button
            btnFavorite.setOnClickListener { onFavoriteClick(combinedProduct.product.id) }
            //handling visibility of btnSuccessAdded
            btnSuccessAdded.isVisible= combinedProduct.isInCart

            //handling click on add to card button
            btnAddToCard.setOnClickListener { onAddToCartClick(combinedProduct.product.id) }


        }?: shimmerLayout.startShimmer()

    }
}
