package com.example.mystore.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.mystore.R
import com.example.mystore.databinding.FragmentCartBinding
import com.example.mystore.data.model.ui.UiProduct
import com.example.mystore.data.model.ui.UiProductInCart
import com.example.mystore.ui.BaseFragment
import com.example.mystore.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class CartFragment: BaseFragment(R.layout.fragment_cart) {

    private var _binding : FragmentCartBinding? = null
    private val binding by lazy { _binding!! }
    private val viewModel by viewModels<CartFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentCartBinding.bind(view)

        val controller = CartFragmentEpoxyController(viewModel , ::onGoShoppingClick)
        binding.epoxyRecyclerView.setController(controller)


        // what we care about is only that product which are in in cart
       val uiProductInCard =  viewModel.uiProductListReducer.reduce(store = viewModel.store).map { uiProducts ->
           uiProducts.filter { it.isInCart }
       }

        combine(
            uiProductInCard ,
            viewModel.store.stateFlow.map { it.cartQuantitiesMap }
        ){uiProducts , quantityMap ->
            uiProducts.map {
                UiProductInCart(
                    uiProduct = it ,
                    quantity = quantityMap[it.product.id] ?: 1
                )
            }
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){
            val viewState = if (it.isEmpty()){
                UiState.Empty
            }else{
                UiState.NotEmpty(it)
            }
            controller.setData(viewState)
        }










    }//FUN

    sealed interface UiState {
        object Empty : UiState
        data class NotEmpty(val products: List<UiProductInCart>) : UiState
    }

    private fun onGoShoppingClick(){
        (activity as? MainActivity)?.navigateToTab(R.id.productListFragment)
    }










    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}