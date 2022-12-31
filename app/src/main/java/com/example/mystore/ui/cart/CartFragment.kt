package com.example.mystore.ui.cart

import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.EpoxyTouchHelper
import com.example.mystore.R
import com.example.mystore.data.model.ui.UiProductInCart
import com.example.mystore.databinding.FragmentCartBinding
import com.example.mystore.ui.MainActivity
import com.example.mystore.ui.cart.epoxy.CartEpoxyModel
import com.example.mystore.ui.cart.epoxy.CartFragmentEpoxyController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Float.max
import java.math.BigDecimal
import java.text.NumberFormat

@AndroidEntryPoint
class CartFragment: Fragment(R.layout.fragment_cart) {

    private var _binding : FragmentCartBinding? = null
    private val binding by lazy { _binding!! }
    private val viewModel by viewModels<CartFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentCartBinding.bind(view)

        val controller = CartFragmentEpoxyController(viewModel , ::onGoShoppingClick)
        binding.epoxyRecyclerView.setController(controller)

        swipeToDelete()


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
            updateTotalLayout(it)
        }



















    }//FUN

    private fun updateTotalLayout(uiProductInCart: List<UiProductInCart>){
        val currentFormatter = NumberFormat.getCurrencyInstance()
        val totalAmount  =  uiProductInCart.sumOf { BigDecimal(it.quantity) * it.uiProduct.product.price }
        val description = "${uiProductInCart.size} items for ${currentFormatter.format(totalAmount)}"
        binding.tvTotalDescription.text = description
        binding.btnCheckout.isEnabled = uiProductInCart.isNotEmpty()
    }

    private fun swipeToDelete(){
        //swipe to delete
        EpoxyTouchHelper
            .initSwiping(binding.epoxyRecyclerView)
            .right()
            .withTarget(CartEpoxyModel::class.java)
            .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<CartEpoxyModel>(){
                override fun onSwipeCompleted(
                    model: CartEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    viewModel.viewModelScope.launch {
                        model?.let {epoxyModel->
                            viewModel.store.update {
                                return@update viewModel.uiProductInCartUpdater.onAddToCart(
                                    productId = epoxyModel.uiProductInCart.uiProduct.product.id ,
                                    currentState = it
                                )
                            }
                        }
                    }
                }

                //in order to show the swipe to delete text view while swiping
                override fun onSwipeProgressChanged(
                    model: CartEpoxyModel?,
                    itemView: View?,
                    swipeProgress: Float,
                    canvas: Canvas?
                ) {
                    itemView?.findViewById<View>(R.id.swipeToDismiss)?.apply {
                        translationX = max(-itemView.translationX , -measuredWidth.toFloat())
                        alpha = 5f * swipeProgress //sets the transmission speed to fade in
                    }
                }

            })
    }



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