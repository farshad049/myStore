package com.example.mystore.ui.productList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.mystore.R
import com.example.mystore.databinding.FragmentProductListBinding
import com.example.mystore.ui.productList.epoxy.ProductEpoxyController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class  ProductListFragment : Fragment(R.layout.fragment_product_list) {
    private var _binding : FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by viewModels()

    @Inject
    lateinit var uiStateGenerator: ProductListFragmentUiStateGenerator

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        _binding = FragmentProductListBinding.bind(view)

        val controller = ProductEpoxyController(viewModel)
        binding.epoxyRecyclerView.setController(controller)




        combine(
            viewModel.uiProductListReducer.reduce(viewModel.store),
            viewModel.store.stateFlow.map { it.productFilterInfo },
        ){uiProducts, productFilterInfo->

            uiStateGenerator.generate(uiProducts , productFilterInfo)

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