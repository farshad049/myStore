package com.example.mystore.ui.productList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.mystore.R
import com.example.mystore.arch.ProductViewModel
import com.example.mystore.databinding.FragmentProductListBinding
import com.example.mystore.model.ui.ProductAndFilterUiState
import com.example.mystore.model.ui.UiFilter
import com.example.mystore.model.ui.UiProduct
import com.example.mystore.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class ProductListFragment : BaseFragment(R.layout.fragment_product_list) {
    private var _binding : FragmentProductListBinding? = null
    private val binding by lazy { _binding!! }

    private val viewModel: ProductViewModel by viewModels()

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        _binding = FragmentProductListBinding.bind(view)

        val controller = ProductEpoxyController(viewModel)
        binding.epoxyRecyclerView.setController(controller)
        //set controller.data to empty list in order to handle loading state in epoxy controller
        //controller.setData(emptyList()) todo



        combine(
            viewModel.store.stateFlow.map { it.products } ,
            viewModel.store.stateFlow.map { it.favoriteProductIds } ,
            viewModel.store.stateFlow.map { it.expandedProductIds } ,
            viewModel.store.stateFlow.map { it.productFilterInfo }
        ){listOfProducts , setOfFavoriteProducts , setOfExpandedProducts , productFilterInfo ->

            //making a list with type of UiProduct
           val uiProducts = listOfProducts.map {
                UiProduct(
                    it ,
                    setOfFavoriteProducts.contains(it.id) ,
                    setOfExpandedProducts.contains(it.id)
                )
            }

            //making a list with type of UiFilter
            val uiFilter = productFilterInfo.filters.map {filter->
                UiFilter(
                    filter = filter ,
                    isSelected = productFilterInfo.selectedFilter?.equals(filter) == true  //while making the list, it checks if this pedicular filter is selected , then send it to recycler vies as a selected one
                )
            }.toSet()

            return@combine if (productFilterInfo.selectedFilter == null){
                ProductAndFilterUiState(
                    filters = uiFilter ,
                    products = uiProducts
                )
            }else{
                ProductAndFilterUiState(
                    filters = uiFilter,
                    products = uiProducts.filter { it.product.category == productFilterInfo.selectedFilter.filterOriginalName }
                )
            }


        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){
            controller.setData(it)
        }

        viewModel.refreshProducts()












    }//FUN













    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}