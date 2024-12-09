package com.kocerlabs.onlineshopmvvm.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kocerlabs.onlineshopmvvm.databinding.FragmentIntroBinding
import com.kocerlabs.simplifiedcodingmvvm.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment : BaseFragment<FragmentIntroBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIntroBinding = FragmentIntroBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            startBtn.setOnClickListener {
                val action = IntroFragmentDirections.actionIntroFragmentToDashboardFragment()
                findNavController().navigate(action)
            }
        }
    }


}