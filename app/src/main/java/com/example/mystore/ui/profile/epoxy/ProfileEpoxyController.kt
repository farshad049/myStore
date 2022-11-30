package com.example.mystore.ui.profile.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.example.mystore.R
import com.example.mystore.data.model.domain.DomainUser
import com.example.mystore.epoxy.DividerEpoxyModel
import com.example.mystore.ui.profile.UserProfileItemGenerator
import com.example.mystore.util.toPx

class ProfileEpoxyController(
    private val profileItemList : UserProfileItemGenerator,
    private val onClicks : ProfileFragmentOnClicks
): TypedEpoxyController<DomainUser?>() {

    override fun buildModels(data: DomainUser?) {

        if (data == null){
            ProfileSignedOutEpoxyModel(
                onSignIn = {username , password ->
                onClicks.onSignIn(username , password)
            } ).id("signed_out_state").addTo(this)
        }else{
            profileItemList.buildItems(data).forEach {items->
                ProfileSignedInEpoxyModel(
                    iconRes = items.iconRes,
                    headerText = items.headerText,
                    infoText = items.infoText,
                    onClick = {
                        onClicks.onProfileItemSelected(items.iconRes)
                    }
                ).id(items.headerText).addTo(this)

                DividerEpoxyModel(horizontalMargin = 16.toPx()).id("divider_${items.iconRes}").addTo(this)
            }

            ProfileSignedInEpoxyModel(
                iconRes = R.drawable.ic_round_logout_24 ,
                headerText = "Logout" ,
                infoText = "Sign out of your account" ,
                onClick = {
                    onClicks.onProfileItemSelected(R.drawable.ic_round_logout_24)
                }
            ).id("logout").addTo(this)
        }
    }

}