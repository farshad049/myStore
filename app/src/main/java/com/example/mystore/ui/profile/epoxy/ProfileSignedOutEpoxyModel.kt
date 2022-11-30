package com.example.mystore.ui.profile.epoxy

import com.example.mystore.R
import com.example.mystore.databinding.ModelProfileSignedOutBinding
import com.example.mystore.epoxy.ViewBindingKotlinModel

data class ProfileSignedOutEpoxyModel(
    val onSignIn: (String, String) -> Unit
) : ViewBindingKotlinModel<ModelProfileSignedOutBinding>(R.layout.model_profile_signed_out) {

    override fun ModelProfileSignedOutBinding.bind() {
        btnLogin.setOnClickListener {
            onSignIn("donero" , "ewedon")
        }
    }
}