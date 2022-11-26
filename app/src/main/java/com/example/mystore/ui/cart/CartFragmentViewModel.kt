package com.example.mystore.ui.cart

import androidx.lifecycle.ViewModel
import com.example.mystore.redux.ApplicationState
import com.example.mystore.redux.Store
import com.example.mystore.redux.reducer.UiProductListReducer
import com.example.mystore.redux.updater.UiAddToCartUpdater
import com.example.mystore.redux.updater.UiProductFavoriteUpdater
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    val store : Store<ApplicationState> ,
    val uiProductListReducer: UiProductListReducer ,
    val uiProductFavoriteUpdater: UiProductFavoriteUpdater ,
    val uiAddToCartUpdater: UiAddToCartUpdater
):ViewModel() {

}