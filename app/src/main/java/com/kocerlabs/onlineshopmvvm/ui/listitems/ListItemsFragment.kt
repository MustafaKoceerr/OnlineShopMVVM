package com.kocerlabs.onlineshopmvvm.ui.listitems

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.kocerlabs.onlineshopmvvm.R
import com.kocerlabs.onlineshopmvvm.data.network.model.ProductsModel
import com.kocerlabs.onlineshopmvvm.databinding.FragmentListItemsBinding
import com.kocerlabs.onlineshopmvvm.ui.dashboard.DashboardFragmentDirections
import com.kocerlabs.onlineshopmvvm.ui.dashboard.MainViewModel
import com.kocerlabs.simplifiedcodingmvvm.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListItemsFragment : BaseFragment<FragmentListItemsBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListItemsBinding = FragmentListItemsBinding.inflate(layoutInflater, container, false)

    private val TAG = "ListItemsFragment"
    private val viewModel: MainViewModel by viewModels()
    private var id: String = ""
    private var title: String = ""

    val args: ListItemsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getBundle()
        initList()

        binding.backBtn.setOnClickListener {
            val action = ListItemsFragmentDirections.actionListItemsFragmentToDashboardFragment()
            findNavController().navigate(action)
        }
    }


    private fun initList() {
        with(binding) {
            progressBarList.visibility = View.VISIBLE
            viewModel.recommendation.observe(viewLifecycleOwner, Observer {
                viewList.layoutManager = GridLayoutManager(requireContext(), 2)
                viewList.adapter = ListItemsAdapter(it, ::goToDetails)
                progressBarList.visibility = View.GONE
            })
            viewModel.loadFiltered(id)
        }
    }


    private fun getBundle() {
        id = args.id
        title = args.title
    }


    private fun goToDetails(item: ProductsModel) {
        Log.d(TAG, "Details item :$item")
        val action =
            DashboardFragmentDirections.actionDashboardFragmentToDetailFragment(item)

        findNavController().navigate(action)
    }


}