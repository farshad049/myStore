package com.example.mystore.ui

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.viewModelScope
import coil.load
import com.airbnb.epoxy.TypedEpoxyController
import com.example.mystore.R
import com.example.mystore.arch.ProductViewModel
import com.example.mystore.databinding.ModelProductItemBinding
import com.example.mystore.epoxy.ViewBindingKotlinModel
import com.example.mystore.model.domain.DomainProduct
import com.example.mystore.model.ui.UiProduct
import kotlinx.coroutines.launch

class ProductEpoxyController(
    private val viewModel: ProductViewModel
):TypedEpoxyController<List<UiProduct>>() {

    override fun buildModels(data: List<UiProduct>?) {
        if (data.isNullOrEmpty()){
            repeat(7) {
                val epoxyId = it + 1
                ProductEpoxyModel(combinedProduct = null,::onFavoriteClick).id(epoxyId).addTo(this)
            }
            return
        }

        data.forEach {
            ProductEpoxyModel(it,::onFavoriteClick).id(it.product.id).addTo(this)
        }
    }











    data class ProductEpoxyModel(val combinedProduct: UiProduct?,val onClick:(Int) -> Unit)
        :ViewBindingKotlinModel<ModelProductItemBinding>(R.layout.model_product_item) {
        override fun ModelProductItemBinding.bind() {
            shimmerLayout.isVisible = combinedProduct == null // if product is null then set shimmerLayout to be visible
            cardProduct.isInvisible = combinedProduct == null

            combinedProduct?.let { combinedProduct->
                shimmerLayout.stopShimmer()

                tvTitle.text=combinedProduct.product.title
                tvCategory.text=combinedProduct.product.category
                tvDescription.text=combinedProduct.product.description
                tvPrice.text= combinedProduct.product.price.toString()
                progressImage.isVisible=true
                ivProduct.load(combinedProduct.product.image){
                    listener { request, result ->
                        progressImage.isGone=true
                    }
                }

                val imageRes=if (combinedProduct.isFavorite) {
                    R.drawable.ic_round_favorite_24
                } else {
                    R.drawable.ic_round_favorite_border_24
                }

                btnFavorite.setIconResource(imageRes )

                btnFavorite.setOnClickListener { onClick(combinedProduct.product.id) }

            }?: shimmerLayout.startShimmer()

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





}