package com.example.mystore.ui.productList

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.example.mystore.arch.ProductViewModel
import com.example.mystore.epoxy.FilterEpoxyModel
import com.example.mystore.epoxy.ProductEpoxyModel
import com.example.mystore.model.domain.Filter
import com.example.mystore.model.ui.ProductAndFilterUiState
import com.example.mystore.model.ui.UiFilter
import com.example.mystore.model.ui.UiProduct
import kotlinx.coroutines.launch

class ProductEpoxyController(
    private val viewModel: ProductViewModel
):TypedEpoxyController<ProductAndFilterUiState>() {

    override fun buildModels(data: ProductAndFilterUiState?) {
        if (data == null){
            repeat(7) {
                val epoxyId = it + 1
                ProductEpoxyModel(uiProduct = null,::onFavoriteClick,::onExpandClick).id(epoxyId).addTo(this)
            }
            return
        }

        val filterList = data.filters.map {
            FilterEpoxyModel(it , ::onFilterClick).id(it.filter.filterOriginalName)
        }
        CarouselModel_()
            .models(filterList)
            .id("filters")
            .addTo(this)



        data.products.forEach {
            ProductEpoxyModel(it,::onFavoriteClick,::onExpandClick).id(it.product.id).addTo(this)
        }
    }
















    private fun onFavoriteClick(selectedFavoriteItemId:Int){
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState->
                val currentFavoriteIds= currentState.favoriteProductIds
                val newFavoriteIds= if (currentFavoriteIds.contains(selectedFavoriteItemId)){
                    //if we this item is already favorite,then unfavorite it
                    currentFavoriteIds.filter { it != selectedFavoriteItemId }.toSet()
                }else{
                    currentFavoriteIds + setOf(selectedFavoriteItemId)
                }
                return@update currentState.copy(favoriteProductIds = newFavoriteIds)
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





}