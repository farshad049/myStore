package com.example.mystore.ui.profile.epoxy

import com.example.mystore.R
import com.example.mystore.databinding.ModelProfileSignedOutBinding
import com.example.mystore.epoxy.ViewBindingKotlinModel

data class ProfileSignedOutEpoxyModel(
    val onSignIn: (String, String) -> Unit,
    val errorMessage : String?
) : ViewBindingKotlinModel<ModelProfileSignedOutBinding>(R.layout.model_profile_signed_out) {

    override fun ModelProfileSignedOutBinding.bind() {
        passwordLayout.error = errorMessage
        btnSignIn.setOnClickListener {
            val username = usernameEditText.text?.toString()
            val password = passwordEditText.text?.toString()

            if (username.isNullOrBlank()  || password.isNullOrBlank()){
                passwordLayout.error = "please enter a password"
                return@setOnClickListener
            }
            onSignIn(username , password)
        }
    }

    override fun ModelProfileSignedOutBinding.unbind(){
        usernameEditText.text = null
        passwordEditText.text = null
    }
}