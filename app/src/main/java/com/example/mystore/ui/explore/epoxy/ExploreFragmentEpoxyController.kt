package com.example.mystore.ui.explore.epoxy

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.TypedEpoxyController
import com.example.mystore.epoxy.LoadingEpoxyModel
import com.example.mystore.ui.explore.model.ExploreFragmentViewState

class ExploreFragmentEpoxyController(
    private val onClicks: ExploreFragmentOnClicks
): TypedEpoxyController<ExploreFragmentViewState>() {

    override fun buildModels(data: ExploreFragmentViewState?) {

        when (data){
            is ExploreFragmentViewState.Loading -> setupLoadingState()
            is ExploreFragmentViewState.Error -> setupErrorState(data)
            is ExploreFragmentViewState.Success -> setupSuccessState(data)
        }

    }







    private fun setupLoadingState(){
        //implement loading state
        LoadingEpoxyModel().id("kkgk").addTo(this)
    }

    private fun setupErrorState(data: ExploreFragmentViewState.Error){
        //implement error state
    }

    private fun setupSuccessState(data: ExploreFragmentViewState.Success){

        HeaderImageEpoxyModel(data.selectedUiProduct.product.image)
            .id("header_image")
            .addTo(this)


        val circleImageList= data.allUiProducts.map { uiProduct ->
            CircleImageEpoxyModel(
                imageUrl = uiProduct.product.image,
                isSelected = uiProduct.product.id == data.selectedUiProduct.product.id,
                onClick = { onClicks.onProductSelected(uiProduct.product.id) }
            ).id("image_${uiProduct.product.id}")
        }
        CarouselModel_().models(circleImageList).id("carousel").addTo(this)


        ProductHeaderDescriptionEpoxyModel(data.selectedUiProduct)
            .id(data.selectedUiProduct.product.id)
            .addTo(this)


        ProductFooterEpoxyModel(
            quantity = data.quantity,
            uiProduct = data.selectedUiProduct,
            addToCart = {
                onClicks.onAddToCart(productId = data.selectedUiProduct.product.id)
            },
            onQuantityUpdate = { newQuantity ->
                onClicks.onQuantityChanged(
                    productId = data.selectedUiProduct.product.id,
                    quantity = newQuantity
                )
            }
        ).id("footer_${data.selectedUiProduct.product.id}").addTo(this)

    }







}