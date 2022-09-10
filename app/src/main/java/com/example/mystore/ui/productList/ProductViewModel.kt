package com.example.mystore.ui.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.arch.ProductRepository
import com.example.mystore.model.domain.Filter
import com.example.mystore.redux.ApplicationState
import com.example.mystore.redux.Store
import com.example.mystore.redux.reducer.UiProductListReducer
import com.example.mystore.util.FilterGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository : ProductRepository,
    val store : Store<ApplicationState>,
    private val filterGenerator: FilterGenerator ,
    val uiProductListReducer: UiProductListReducer
    ) : ViewModel() {


    fun refreshProducts() = viewModelScope.launch {
            val response = repository.getProduct()
            val filters :Set<Filter> = filterGenerator.generateFrom(response)

            store.update {applicationState->
                return@update applicationState.copy(
                    products = response ,
                    //we init this here, because response is required to make this item
                    productFilterInfo = ApplicationState.ProductFilterInfo(
                        filters = filters,
                        selectedFilter = null
                    )
                )
            }
        }





}