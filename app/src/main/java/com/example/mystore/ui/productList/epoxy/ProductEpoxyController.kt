package com.example.mystore.ui.productList.epoxy

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.example.mystore.data.model.ui.UiFilter
import com.example.mystore.ui.productList.ProductAndFilterUiState
import com.example.mystore.ui.productList.ProductViewModel
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import java.util.*

class ProductEpoxyController(
    private val viewModel: ProductViewModel
):TypedEpoxyController<ProductAndFilterUiState>() {

    override fun buildModels(data: ProductAndFilterUiState) {

        when (data) {

            is ProductAndFilterUiState.Success -> {

                val filterList = data.filters.map {
                    FilterEpoxyModel(it, ::onFilterClick).id(it.filterOriginalAndDisplay.filterOriginalName)
                }
                CarouselModel_()
                    .models(filterList)
                    .id("filters")
                    .addTo(this)


                data.products.forEach {
                    ProductEpoxyModel(it, ::onFavoriteClick, ::onExpandClick, ::onAddToCartClick).id(it.product.id)
                        .addTo(this)
                }
            }




            is ProductAndFilterUiState.Loading -> {
                repeat(7) {
                    ProductEpoxyModel(
                        uiProduct = null,
                        ::onFavoriteClick,
                        ::onExpandClick,
                        ::onAddToCartClick).id(UUID.randomUUID().toString()
                    ).addTo(this)
                }
                return
               }
            else -> {throw RuntimeException("unhandled branch! $data")}
             }
        }









    private fun onFavoriteClick(selectedFavoriteItemId: Int){
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState->
                return@update viewModel.uiProductFavoriteUpdater.onProductFavorited(
                    productId = selectedFavoriteItemId ,
                    currentState = currentState
                )
            }
        }
    }

    private fun onExpandClick(selectedExpandedItemId:Int){
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState->
                val currentExpandIds= currentState.expandedProductIds
                val newExpandIds= if (currentExpandIds.contains(selectedExpandedItemId)){
                    //if we this item is already expand,then unExpand it
                    currentExpandIds - selectedExpandedItemId
                }else{
                    currentExpandIds + selectedExpandedItemId
                }
                return@update currentState.copy(expandedProductIds = newExpandIds)
            }
        }
    }

    private fun onFilterClick(filter: UiFilter.FilterOriginalAndDisplay){
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState->
                val currentSelectedFilter = currentState.productFilterInfo.selectedFilter
                val newFilter= if (currentSelectedFilter != filter) filter else null
                return@update currentState.copy(
                    productFilterInfo = currentState.productFilterInfo.copy(
                        selectedFilter = newFilter
                    )
                )
            }
        }
    }

    private fun onAddToCartClick(selectedProductId: Int){
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState->
                return@update viewModel.uiProductInCartUpdater.onAddToCart(selectedProductId , currentState)
            }
        }
    }





}