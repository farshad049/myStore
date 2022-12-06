package com.example.mystore.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.mystore.R
import com.example.mystore.databinding.FragmentProfileBinding
import com.example.mystore.ui.profile.epoxy.ProfileEpoxyController
import com.example.mystore.ui.profile.epoxy.ProfileFragmentOnClicks
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint

class ProfileFragment:Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding by lazy { _binding!! }
    private val profileViewModel by viewModels<ProfileViewModel>()

    @Inject
    lateinit var userProfileItemGenerator: UserProfileItemGenerator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentProfileBinding.bind(view)

        val onClicks= ProfileFragmentOnClicks(profileViewModel)
        val controller = ProfileEpoxyController(userProfileItemGenerator,onClicks)
        binding.epoxyRecyclerView.setController(controller)

        profileViewModel.store.stateFlow.map { it.user }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){ user->
            controller.setData(user)
            binding.tvWelcome.text = user.getGreetingMessage()
            binding.tvEmail.text = user.getEmail()
        }


        //run the intent based on which item does user clicked
        profileViewModel.intentFlow.filterNotNull().asLiveData().observe(viewLifecycleOwner){intent->
            startActivity(intent)
        }








    }//FUN







    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}