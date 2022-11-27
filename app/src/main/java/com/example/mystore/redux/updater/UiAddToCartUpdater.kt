package com.example.mystore.redux.updater

import com.example.mystore.redux.ApplicationState
import javax.inject.Inject

class UiAddToCartUpdater @Inject constructor() {

    fun onAddToCart(productId : Int , currentState: ApplicationState):ApplicationState{
        val currentInCardIds= currentState.inCartProductIds
        var currentQuantity = currentState.cartQuantitiesMap
        val newInCartIds= if (currentInCardIds.contains(productId)){
            currentQuantity = emptyMap() // after delete this item from list, also set the quantity to default value which is emptyMap
            currentInCardIds - productId
        }else{
            currentInCardIds + productId
        }
        return currentState.copy(
            inCartProductIds = newInCartIds ,
            cartQuantitiesMap = currentQuantity
        )
    }
}