package com.example.mystore.redux.updater

import com.example.mystore.redux.ApplicationState
import javax.inject.Inject

class UiProductFavoriteUpdater @Inject constructor() {

    fun onProductFavorited(productId : Int,currentState : ApplicationState):ApplicationState{
        val currentFavoriteIds= currentState.favoriteProductIds
        val newFavoriteIds= if (currentFavoriteIds.contains(productId)){
            //if we this item is already favorite,then unfavorite it
            currentFavoriteIds - productId
        }else{
            currentFavoriteIds + productId
        }
        return currentState.copy(favoriteProductIds = newFavoriteIds)

    }
}