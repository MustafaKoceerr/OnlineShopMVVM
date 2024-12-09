package com.kocerlabs.onlineshopmvvm.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kocerlabs.onlineshopmvvm.data.network.model.ProductsModel
import com.kocerlabs.onlineshopmvvm.databinding.FragmentDetailBinding
import com.kocerlabs.onlineshopmvvm.helper.ManagementCart
import com.kocerlabs.simplifiedcodingmvvm.ui.base.BaseFragment

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)

    val args: DetailFragmentArgs by navArgs()
    private lateinit var item: ProductsModel
    private var numberOrder = 1
    private lateinit var managementCart: ManagementCart


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        managementCart = ManagementCart(requireContext())

        getBundle()
        initList()


    }

    private fun initList() {
        val modelList = mutableListOf<String>()
        item.model.map { model -> modelList.add(model) }

        with(binding.modelList) {
            adapter = SelectModelAdapter(modelList)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        val picList = mutableListOf<String>()
        item.picUrl.map { pic -> picList.add(pic) }

        Glide.with(requireContext())
            .load(picList[0])
            .into(binding.img)

        with(binding.picList) {
            adapter = PicAdapter(picList, { selectedImageUrl ->
                Glide.with(requireContext())
                    .load(selectedImageUrl)
                    .into(binding.img)

            })
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun getBundle() {
        item = args.obj
        with(binding) {
            titleTxt.text = item.title
            descriptionTxt.text = item.description
            priceTxt.text = "$" + item.price
            ratingTxt.text = "${item.rating} Rating"
            addToCartBtn.setOnClickListener {
                item.numberInCart = numberOrder
                managementCart.insertItem(item)
            }
            backBtn.setOnClickListener {
                val action = DetailFragmentDirections.actionDetailFragmentToDashboardFragment()
                findNavController().navigate(action)
            }
            cartBtn.setOnClickListener {
                val action = DetailFragmentDirections.actionDetailFragmentToCartFragment()
                findNavController().navigate(action)
            }

        }
    }

}