package com.example.mystore.ui.cart.epoxy

import com.example.mystore.R
import com.example.mystore.databinding.ModelCartEmptyBinding
import com.example.mystore.epoxy.ViewBindingKotlinModel

data class CartEmptyEpoxyModel(
    private val onButtonClick: () -> Unit
) : ViewBindingKotlinModel<ModelCartEmptyBinding>(R.layout.model_cart_empty) {

    override fun ModelCartEmptyBinding.bind() {
        button.setOnClickListener {onButtonClick()}
    }
}
