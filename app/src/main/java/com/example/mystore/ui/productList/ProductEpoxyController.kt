package com.example.mystore.ui.productList

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.example.mystore.epoxy.FilterEpoxyModel
import com.example.mystore.epoxy.ProductEpoxyModel
import com.example.mystore.model.domain.Filter
import com.example.mystore.model.ui.ProductAndFilterUiState
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

    private fun onAddToCartClick(selectedProductId: Int){
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState->
                val currentInCardIds= currentState.inCartProductIds
                val newInCartIds= if (currentInCardIds.contains(selectedProductId)){
                    currentInCardIds.filter { it != selectedProductId }.toSet()
                }else{
                    currentInCardIds + setOf(selectedProductId)
                }
                return@update currentState.copy(inCartProductIds = newInCartIds)
            }
        }
    }





}