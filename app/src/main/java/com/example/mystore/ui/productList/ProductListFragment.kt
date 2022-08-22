package com.example.mystore.ui.productList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.mystore.R
import com.example.mystore.arch.ProductViewModel
import com.example.mystore.databinding.FragmentProductListBinding
import com.example.mystore.model.ui.UiProduct
import com.example.mystore.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class ProductListFragment: BaseFragment(R.layout.fragment_product_list) {
    private var _binding: FragmentProductListBinding? = null
    private val binding by lazy { _binding!! }

    private val viewModel: ProductViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentProductListBinding.bind(view)

        val controller = ProductEpoxyController(viewModel)
        binding.epoxyRecyclerView.setController(controller)
        //set controller.data to empty list in order to handle loading state in epoxy controller
        controller.setData(emptyList())



        combine(
            viewModel.store.stateFlow.map { it.products },
            viewModel.store.stateFlow.map { it.favoriteProductIds },
            viewModel.store.stateFlow.map { it.expandedProductIds }
        ){listOfProducts,setOfFavoriteProducts,setOfExpandedProducts ->
            listOfProducts.map {
                UiProduct(
                    it,
                    setOfFavoriteProducts.contains(it.id),
                    setOfExpandedProducts.contains(it.id)

                )//second element is > if in that set of product eny product is favorite
            }
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){
            controller.setData(it)
        }

        viewModel.refreshProducts()



    }






    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}