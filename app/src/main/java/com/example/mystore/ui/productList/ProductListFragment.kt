package com.example.mystore.ui.productList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.mystore.R
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
class  ProductListFragment : BaseFragment(R.layout.fragment_product_list) {
    private var _binding : FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by viewModels()

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        _binding = FragmentProductListBinding.bind(view)

        val controller = ProductEpoxyController(viewModel)
        binding.epoxyRecyclerView.setController(controller)




        combine(
            viewModel.uiProductListReducer.reduce(viewModel.store),
            viewModel.store.stateFlow.map { it.productFilterInfo },
        ){UiProducts, productFilterInfo->

            //if list is empty run the shimmer
            if (UiProducts.isEmpty()){
                return@combine ProductAndFilterUiState.Loading
            }


            //making a list with type of UiFilter
            val uiFilter = productFilterInfo.filters.map {filter->
                UiFilter(
                    filter = filter ,
                    isSelected = productFilterInfo.selectedFilter?.equals(filter) == true  //while making the list, it checks if this pedicular filter is selected , then send it to recycler vies as a selected one
                )
            }.toSet()

            val filteredProducts = if (productFilterInfo.selectedFilter == null){
                UiProducts
            }else{
                UiProducts.filter { it.product.category == productFilterInfo.selectedFilter.filterOriginalName }

            }

            return@combine ProductAndFilterUiState.Success(uiFilter , filteredProducts)


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