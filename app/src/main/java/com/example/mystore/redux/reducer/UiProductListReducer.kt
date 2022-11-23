package com.example.mystore.redux.reducer

import com.example.mystore.data.model.ui.UiProduct
import com.example.mystore.redux.ApplicationState
import com.example.mystore.redux.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UiProductListReducer @Inject constructor() {
    fun reduce(store: Store<ApplicationState>) :Flow<List<UiProduct>> {
        return combine(
            store.stateFlow.map { it.products } ,
            store.stateFlow.map { it.favoriteProductIds } ,
            store.stateFlow.map { it.expandedProductIds } ,
            store.stateFlow.map { it.inCartProductIds }
        ){listOfProducts , setOfFavoriteProducts , setOfExpandedProducts  , setOfInCardtProducts->

            //if list is empty run the shimmer
            if (listOfProducts.isEmpty()){
                return@combine emptyList<UiProduct>()
            }


            //making a list with type of UiProduct
            return@combine listOfProducts.map {
                UiProduct(
                    product = it ,
                    isFavorite = setOfFavoriteProducts.contains(it.id) ,
                    isExpanded = setOfExpandedProducts.contains(it.id),
                    isInCart = setOfInCardtProducts.contains(it.id)
                )
            }
        }
    }
}