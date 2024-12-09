package com.kocerlabs.onlineshopmvvm.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.kocerlabs.onlineshopmvvm.data.network.model.ProductsModel
import com.kocerlabs.onlineshopmvvm.data.network.model.SliderModel
import com.kocerlabs.onlineshopmvvm.databinding.FragmentDashboardBinding
import com.kocerlabs.simplifiedcodingmvvm.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {
    private val viewModel: MainViewModel by viewModels()
    private val TAG = "DashboardFragment"
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBanner()
        initCategory()
        initRecommended()

        binding.cartBtn.setOnClickListener(::initBottomMenu)
    }

    private fun banners(images: List<SliderModel>) {
        with(binding.viewPager2) {
            adapter = SliderAdapter(images)
            clipToPadding =
                false // çocukların pading area'ya çizilmesine izin verir. Recycler view özelliğidir.
            clipChildren = false
            offscreenPageLimit = 3
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            val compositePageTransformer = CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(40)) // sayfa değiştirirken aralarındaki boşluğu belirler.
            }
            /**
             * CompositePageTransformer kullanılarak, aynı anda birden fazla dönüşüm uygulanabiliyor.
             * compositePageTransformer burada birden fazla dönüşüm (transformasyon) ekleyerek sayfa geçişlerini özelleştiriyor.
             * Şu an yalnızca MarginPageTransformer eklenmiş, ancak başka dönüşümler de eklenebilir.
             */
            setPageTransformer(compositePageTransformer)
        }
        /**
         * setPageTransformer(compositePageTransformer) metodu, ViewPager2'deki her sayfa için özel bir dönüşüm (transformasyon) işlemi uygular.
         *
         *
         * setPageTransformer(compositePageTransformer) metodu, ViewPager2 üzerinde sayfa geçişlerini transformasyon (dönüşüm) uygulamak için kullanılır.
         * Bu işlem, her sayfanın kaydırılma hareketi sırasında özel animasyonlar ve görsel efektler uygulamanızı sağlar.

         *PageTransformer, ViewPager2 gibi sayfa kaydırma bileşenlerinde kullanılan bir arayüzdür.
         *  Bu arayüz, bir sayfa kaydırıldıkça sayfalar üzerinde dönüşüm (transformasyon) işlemi uygulamak için kullanılır. Bu dönüşümler, sayfa geçişi sırasında sayfanın büyüklüğünü değiştirme, sayfanın konumunu kaydırma, sayfanın saydamlığını değiştirme gibi görsel efektleri içerebilir.
         */

        with(binding) {
            if (images.size > 1) {
                dotIndicator.visibility = View.VISIBLE
                dotIndicator.attachTo(viewPager2)
            }
        }
    }

    private fun initBanner() {
        with(binding) {
            progressBarSlider.visibility = View.VISIBLE
            viewModel.banner.observe(viewLifecycleOwner, Observer {
                banners(it)
                progressBarSlider.visibility = View.GONE
            })
            viewModel.loadBanners()
        }
    }


    private fun initCategory() {
        with(binding) {
            progressBarCategory.visibility = View.VISIBLE
            viewModel.category.observe(viewLifecycleOwner, Observer {
                viewCategory.layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                viewCategory.adapter = CategoryAdapter(it, ::goToCategory)
                progressBarCategory.visibility = View.GONE
            })
            viewModel.loadCategories()
        }
    }


    private fun initRecommended() {
        with(binding) {
            progressBarRecommend.visibility = View.VISIBLE
            viewModel.recommendation.observe(viewLifecycleOwner, Observer {
                viewRecommendation.layoutManager = GridLayoutManager(
                    requireContext(),
                    2,
                )
                viewRecommendation.adapter = RecommendedAdapter(it, ::goToDetails)
                progressBarRecommend.visibility = View.GONE
            })
            viewModel.loadRecommended()
        }
    }


    private fun goToDetails(item: ProductsModel) {
        Log.d(TAG, "Details item :$item")
        val action =
            DashboardFragmentDirections.actionDashboardFragmentToDetailFragment(item)

        findNavController().navigate(action)
    }


    private fun goToCategory(id: String, title: String) {
        val action =
            DashboardFragmentDirections.actionDashboardFragmentToListItemsFragment(
                id = id,
                title = title
            )

        findNavController().navigate(action)
    }


    private fun initBottomMenu(view: View) {
        val action =
            DashboardFragmentDirections.actionDashboardFragmentToCartFragment()
        findNavController().navigate(action)
    }
}