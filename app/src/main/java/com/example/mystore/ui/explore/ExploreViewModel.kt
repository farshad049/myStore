package com.example.mystore.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.model.ui.UiProduct
import com.example.mystore.redux.ApplicationState
import com.example.mystore.redux.Store
import com.example.mystore.redux.reducer.UiProductListReducer
import com.example.mystore.redux.updater.UiProductInCartUpdater
import com.example.mystore.redux.updater.UiProductQuantityUpdater
import com.example.mystore.ui.explore.model.ExploreFragmentViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    val uiProductInCartUpdater: UiProductInCartUpdater,
    val uiProductQuantityUpdater: UiProductQuantityUpdater,
    uiProductListReducer: UiProductListReducer,
): ViewModel() {

    private val uiProductListFlow: Flow<List<UiProduct>> = uiProductListReducer.reduce(store)
    private val cartQuantityFlow: Flow<Map<Int, Int>> = store.stateFlow.map { it.cartQuantitiesMap }
    private val selectedProductIdFlow: Flow<Int> = store.stateFlow.map { it.explorePageMetadata.selectedProductId }


    val uiViewState: StateFlow<ExploreFragmentViewState> =
        combine(
        uiProductListFlow,
        cartQuantityFlow,
        selectedProductIdFlow
    ) { uiProducts, cartQuantitiesMap, selectedProductId ->
        val selectedProduct = uiProducts.find { it.product.id == selectedProductId }

        return@combine if (uiProducts.isEmpty() || selectedProduct == null) {
            ExploreFragmentViewState.Loading
        } else {
            ExploreFragmentViewState.Success(
                selectedUiProduct = selectedProduct,
                quantity = cartQuantitiesMap[selectedProduct.product.id] ?: 1,
                allUiProducts = uiProducts
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ExploreFragmentViewState.Loading
    )


    fun onProductSelected(productId: Int) = viewModelScope.launch {
        store.update { currentApplicationState ->
            return@update currentApplicationState.copy(
                explorePageMetadata = currentApplicationState.explorePageMetadata.copy(
                    selectedProductId = productId
                )
            )
        }
    }




}