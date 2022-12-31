package com.example.mystore.ui.explore.epoxy

import android.widget.SeekBar
import com.example.mystore.R
import com.example.mystore.data.model.ui.UiProduct
import com.example.mystore.databinding.EpoxyModelExploreProductFooterBinding
import com.example.mystore.epoxy.ViewBindingKotlinModel
import kotlin.math.roundToInt

data class ProductFooterEpoxyModel(
    val quantity: Int,
    val uiProduct: UiProduct,
    val addToCart: () -> Unit,
    val onQuantityUpdate: (Int) -> Unit
) : ViewBindingKotlinModel<EpoxyModelExploreProductFooterBinding>(R.layout.epoxy_model_explore_product_footer) {

    override fun EpoxyModelExploreProductFooterBinding.bind() {
        ratingIndicator.progress = (uiProduct.product.rating.rate * 10).roundToInt()
        ratingTextView.text = "${uiProduct.product.rating.rate} / 5.0"

        quantitySeekbar.setOnSeekBarChangeListener(null)
        quantitySeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                quantityTextView.text = "Qty. $progress"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                // Nothing to do
            }

            override fun onStopTrackingTouch(seekbar: SeekBar) {
                onQuantityUpdate(seekbar.progress)
            }
        })
        quantitySeekbar.progress = quantity
        quantityTextView.text = "Qty. $quantity"

        val text = if (uiProduct.isInCart) "Remove from cart" else "Add to cart"
        addToCartButton.text = text
        addToCartButton.setOnClickListener { addToCart() }
    }
}
