package com.example.mystore.ui.profile.epoxy

import androidx.annotation.DrawableRes
import com.example.mystore.R
import com.example.mystore.databinding.ModelProductItemBinding
import com.example.mystore.databinding.ModelProfileItemBinding
import com.example.mystore.epoxy.ViewBindingKotlinModel

data class ProfileSignedInEpoxyModel(
    @DrawableRes val iconRes: Int,
    val headerText: String,
    val infoText: String,
    val onClick: () -> Unit
) : ViewBindingKotlinModel<ModelProfileItemBinding>(R.layout.model_profile_item) {

    override fun ModelProfileItemBinding.bind() {
        ivItem.setImageResource(iconRes)
        tvTitle.text = headerText
        tvDescription.text = infoText
        root.setOnClickListener { onClick() }
    }
}