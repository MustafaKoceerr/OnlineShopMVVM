package com.kocerlabs.onlineshopmvvm.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductsModel(
    var title: String = "",
    var description: String = "",
    var picUrl: List<String> = emptyList(),
    var model: List<String> = emptyList(),
    var price: Double = 0.0,
    var rating: Double = 0.0,
    var numberInCart: Int = 0,
    var showRecommended: Boolean = false,
    var categoryId: Int = 0 ,
) : Parcelable