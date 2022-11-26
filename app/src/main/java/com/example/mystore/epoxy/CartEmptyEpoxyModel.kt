package com.example.mystore.epoxy

import com.example.mystore.R
import com.example.mystore.databinding.ModelCartEmptyBinding

data class CartEmptyEpoxyModel(
    private val onButtonClick: () -> Unit
) : ViewBindingKotlinModel<ModelCartEmptyBinding>(R.layout.model_cart_empty) {

    override fun ModelCartEmptyBinding.bind() {
        button.setOnClickListener {onButtonClick()}
    }
}
