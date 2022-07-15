package com.example.mystore.ui

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import com.airbnb.epoxy.TypedEpoxyController
import com.example.mystore.R
import com.example.mystore.databinding.ModelProductItemBinding
import com.example.mystore.epoxy.ViewBindingKotlinModel
import com.example.mystore.model.domain.Product

class ProductEpoxyController:TypedEpoxyController<List<Product>>() {

    override fun buildModels(data: List<Product>?) {
        if (data.isNullOrEmpty()){
            repeat(7) {
                val epoxyId = it + 1
                ProductEpoxyModel(product = null).id(epoxyId).addTo(this)
            }
            return
        }

        data.forEach { product->
            ProductEpoxyModel(product).id(product.id).addTo(this)
        }


    }

    data class ProductEpoxyModel(val product: Product?)
        :ViewBindingKotlinModel<ModelProductItemBinding>(R.layout.model_product_item) {
        override fun ModelProductItemBinding.bind() {
            shimmerLayout.isVisible = product == null // if product is null then set shimmerLayout to be visible
            cardProduct.isInvisible = product == null

            product?.let {product->
                shimmerLayout.stopShimmer()

                tvTitle.text=product.title
                tvCategory.text=product.category
                tvDescription.text=product.description
                tvPrice.text= product.price.toString()

                progressImage.isVisible=true
                ivProduct.load(product.image){
                    listener { request, result ->
                        progressImage.isGone=true
                    }
                }
            }?: shimmerLayout.startShimmer()

        }
    }


}