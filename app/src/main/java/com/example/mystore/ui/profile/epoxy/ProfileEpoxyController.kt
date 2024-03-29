package com.example.mystore.ui.profile.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.example.mystore.R
import com.example.mystore.data.model.domain.DomainUser
import com.example.mystore.epoxy.DividerEpoxyModel
import com.example.mystore.redux.ApplicationState
import com.example.mystore.ui.profile.UserProfileItemGenerator
import com.example.mystore.util.toPx

class ProfileEpoxyController(
    private val profileItemList : UserProfileItemGenerator,
    private val onClicks : ProfileFragmentOnClicks
): TypedEpoxyController<ApplicationState.UserLoginResponse>() {

    override fun buildModels(data: ApplicationState.UserLoginResponse) {

        if (data is ApplicationState.UserLoginResponse.UnAuthenticated){
            ProfileSignedOutEpoxyModel(
                onSignIn = {username , password ->
                onClicks.onSignIn(username , password) } ,
                errorMessage = data.error
            ).id("signed_out_state").addTo(this)
        }


        if (data is ApplicationState.UserLoginResponse.Authenticated){
            profileItemList.buildItems(data.user).forEach {items->
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