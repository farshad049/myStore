package com.example.mystore.ui.explore.epoxy


import com.example.mystore.R
import com.example.mystore.data.model.ui.UiProduct
import com.example.mystore.databinding.EpoxyModelExploreProductHeaderBinding
import com.example.mystore.epoxy.ViewBindingKotlinModel
import java.text.NumberFormat

data class ProductHeaderDescriptionEpoxyModel(
    val uiProduct: UiProduct
): ViewBindingKotlinModel<EpoxyModelExploreProductHeaderBinding>(
    R.layout.epoxy_model_explore_product_header
) {
    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyModelExploreProductHeaderBinding.bind() {
        productTitleTextView.text = uiProduct.product.title
        productDescriptionTextView.text = uiProduct.product.description
        productCategoryTextView.text = uiProduct.product.category
        productPriceTextView.text = currencyFormatter.format(uiProduct.product.price)
    }
}