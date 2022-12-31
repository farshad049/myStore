package com.example.mystore.ui.productList.epoxy

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.example.mystore.data.model.domain.Filter
import com.example.mystore.ui.productList.ProductAndFilterUiState
import com.example.mystore.ui.productList.ProductViewModel
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import java.util.*

class ProductEpoxyController(
    private val viewModel: ProductViewModel
):TypedEpoxyController<ProductAndFilterUiState>() {

    override fun buildModels(data: ProductAndFilterUiState?) {

        when (data) {

            is ProductAndFilterUiState.Success -> {

                val filterList = data.filters.map {
                    FilterEpoxyModel(it, ::onFilterClick).id(it.filter.filterOriginalName)
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
                    val epoxyId = UUID.randomUUID().toString()
                    ProductEpoxyModel(uiProduct = null, ::onFavoriteClick, ::onExpandClick, ::onAddToCartClick).id(
                        epoxyId
                    ).addTo(this)
                }
                return
               }
            else -> {throw RuntimeException("unhandled branch! $data")}
             }
        }









    private fun onFavoriteClick(selectedFavoriteItemId:Int){
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
                    currentExpandIds.filter { it != selectedExpandedItemId }.toSet()
                }else{
                    currentExpandIds + setOf(selectedExpandedItemId)
                }
                return@update currentState.copy(expandedProductIds = newExpandIds)
            }
        }
    }

    private fun onFilterClick(filter: Filter){
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState->
                val currentSelectedFilter = currentState.productFilterInfo.selectedFilter
                return@update currentState.copy(
                    productFilterInfo = currentState.productFilterInfo.copy(
                        selectedFilter = if (currentSelectedFilter != filter) filter else null
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