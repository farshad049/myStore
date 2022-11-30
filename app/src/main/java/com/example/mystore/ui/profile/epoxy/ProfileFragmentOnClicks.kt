package com.example.mystore.ui.profile.epoxy

import android.util.Log
import com.example.mystore.R
import com.example.mystore.ui.profile.ProfileViewModel


class ProfileFragmentOnClicks (private val viewModel : ProfileViewModel) {

    fun onSignIn(username: String, password: String) {
        viewModel.login(username, password)
    }


    fun onProfileItemSelected(id: Int) {
        when (id) {
            R.drawable.ic_round_phone_24 -> {
                // call intent
            }
            R.drawable.ic_round_location_on_24 -> {
                // location intent
            }
            R.drawable.ic_round_logout_24 -> {
                viewModel.logout()
            }
            else -> {
                Log.i("SELECTION", "Unknown ID -> $id")
            }
        }
    }

}