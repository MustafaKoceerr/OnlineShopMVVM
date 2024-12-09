package com.kocerlabs.onlineshopmvvm.data

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {
    fun loadBanners(): DatabaseReference = firebaseDatabase.getReference("Banner")


    fun loadCategories(): DatabaseReference = firebaseDatabase.getReference("Category")

    fun loadRecommended(): DatabaseReference = firebaseDatabase.getReference("Items")


}
