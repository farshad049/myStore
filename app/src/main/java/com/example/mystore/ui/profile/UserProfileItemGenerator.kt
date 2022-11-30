package com.example.mystore.ui.profile

import androidx.annotation.DrawableRes
import com.example.mystore.R
import com.example.mystore.data.model.domain.DomainUser
import javax.inject.Inject

class UserProfileItemGenerator @Inject constructor() {

    data class UserProfileUiItem(
        @DrawableRes val iconRes: Int,
        val headerText: String,
        val infoText: String
    )

    fun buildItems(user: DomainUser): List<UserProfileUiItem> {
        return buildList {
            add(
                UserProfileUiItem(
                    iconRes = R.drawable.ic_round_person_24,
                    headerText = "Username",
                    infoText = user.username
                )
            )
            add(
                UserProfileUiItem(
                    iconRes = R.drawable.ic_round_phone_24,
                    headerText = "Phone number",
                    infoText = user.phoneNumber
                )
            )
            add(
                UserProfileUiItem(
                    iconRes = R.drawable.ic_round_location_on_24,
                    headerText = "Location",
                    infoText = "${user.address.street}, ${user.address.city}, ${user.address.zipcode}"
                )
            )
        }
    }
}