package com.example.mystore.ui.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import com.example.mystore.R
import com.example.mystore.databinding.FragmentExploreBinding
import com.example.mystore.databinding.FragmentProductListBinding
import com.example.mystore.ui.explore.epoxy.ExploreFragmentEpoxyController
import com.example.mystore.ui.explore.epoxy.ExploreFragmentOnClicks
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment: Fragment(R.layout.fragment_explore) {
    private var _binding : FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val exploreViewModel by viewModels<ExploreViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        _binding = FragmentExploreBinding.bind(view)

        val exploreFragmentOnClicks = ExploreFragmentOnClicks(exploreViewModel)
        val controller = ExploreFragmentEpoxyController(exploreFragmentOnClicks)
        binding.epoxyRecyclerView.setController(controller)

        exploreViewModel.uiViewState.asLiveData().distinctUntilChanged().observe(viewLifecycleOwner){
            controller.setData(it)
        }





    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}