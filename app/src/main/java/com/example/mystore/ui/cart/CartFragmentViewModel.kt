package com.example.mystore.ui.cart

import androidx.lifecycle.ViewModel
import com.example.mystore.redux.ApplicationState
import com.example.mystore.redux.Store
import com.example.mystore.redux.reducer.UiProductListReducer
import com.example.mystore.redux.updater.UiProductInCartUpdater
import com.example.mystore.redux.updater.UiProductFavoriteUpdater
import com.example.mystore.redux.updater.UiProductQuantityUpdater
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    val store : Store<ApplicationState>,
    val uiProductListReducer: UiProductListReducer,
    val uiProductFavoriteUpdater: UiProductFavoriteUpdater,
    val uiProductInCartUpdater: UiProductInCartUpdater,
    val uiProductQuantityUpdater: UiProductQuantityUpdater,
):ViewModel() {

}