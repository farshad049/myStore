package com.example.mystore.redux.updater

import com.example.mystore.redux.ApplicationState
import javax.inject.Inject

class UiAddToCartUpdater @Inject constructor() {

    fun onAddToCart(productId : Int , currentState: ApplicationState):ApplicationState{
        val currentInCardIds= currentState.inCartProductIds
        val newInCartIds= if (currentInCardIds.contains(productId)){
            currentInCardIds - productId
        }else{
            currentInCardIds + productId
        }
        return currentState.copy(inCartProductIds = newInCartIds)
    }
}