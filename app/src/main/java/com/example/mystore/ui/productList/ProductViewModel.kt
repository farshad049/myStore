package com.example.mystore.ui.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.repository.ProductRepository
import com.example.mystore.data.model.domain.Filter
import com.example.mystore.redux.ApplicationState
import com.example.mystore.redux.Store
import com.example.mystore.redux.reducer.UiProductListReducer
import com.example.mystore.redux.updater.UiProductInCartUpdater
import com.example.mystore.redux.updater.UiProductFavoriteUpdater
import com.example.mystore.util.FilterGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    val store : Store<ApplicationState>,
    val uiProductListReducer: UiProductListReducer,
    val uiProductFavoriteUpdater: UiProductFavoriteUpdater,
    val uiProductInCartUpdater: UiProductInCartUpdater,
    private val filterGenerator: FilterGenerator,
    private val repository : ProductRepository,
    ) : ViewModel() {


    fun refreshProducts() = viewModelScope.launch {
        //if list is not empty don't make the network call, this has usage when you already load the data on screen and you want to move to another fragment and don't want to load the data again
        if (store.read { it.products.isNotEmpty() }) return@launch

            val response = repository.getProduct()
            val filters :Set<Filter> = filterGenerator.generateFrom(response)

            store.update {applicationState->
                return@update applicationState.copy(
                    products = response ,
                    //we init this here, because response is required to make this item
                    productFilterInfo = ApplicationState.ProductFilterInfo(
                        filters = filters,
                        selectedFilter = applicationState.productFilterInfo.selectedFilter
                    )
                )
            }
        }





}