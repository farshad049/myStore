package com.example.mystore.ui.productList

import com.example.mystore.data.model.ui.UiFilter
import com.example.mystore.data.model.ui.UiProduct
import com.example.mystore.redux.ApplicationState
import javax.inject.Inject

class ProductListFragmentUiStateGenerator @Inject constructor(){

    fun generate(
        uiProducts : List<UiProduct>,
        productFilterInfo : ApplicationState.ProductFilterInfo //ProductFilterInfo has been defined inside application state
    ):ProductAndFilterUiState{
        //if list is empty run the shimmer
        if (uiProducts.isEmpty()){
            return ProductAndFilterUiState.Loading
        }


        //making a list with type of UiFilter
        val uiFilter = productFilterInfo.filters.map {filter->
            UiFilter(
                filter = filter ,
                isSelected = productFilterInfo.selectedFilter?.equals(filter) == true  //while making the list, it checks if this pedicular filter is selected , then send it to recycler vies as a selected one
            )
        }.toSet()

        val filteredProducts = if (productFilterInfo.selectedFilter == null){
            uiProducts
        }else{
            uiProducts.filter { it.product.category == productFilterInfo.selectedFilter.filterOriginalName }

        }

        return ProductAndFilterUiState.Success(uiFilter , filteredProducts)
    }
}