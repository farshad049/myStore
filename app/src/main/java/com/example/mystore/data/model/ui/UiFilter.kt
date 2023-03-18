package com.example.mystore.data.model.ui



data class UiFilter(
    val filterOriginalAndDisplay: FilterOriginalAndDisplay,
    val isSelected:Boolean
){
    data class FilterOriginalAndDisplay (
        val filterOriginalName:String="",
        val filterDisplayName: String=""
    )
}
