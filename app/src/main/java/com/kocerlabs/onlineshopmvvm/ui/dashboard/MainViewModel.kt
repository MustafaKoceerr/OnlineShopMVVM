package com.kocerlabs.onlineshopmvvm.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.kocerlabs.onlineshopmvvm.data.MainRepository
import com.kocerlabs.onlineshopmvvm.data.network.model.CategoryModel
import com.kocerlabs.onlineshopmvvm.data.network.model.ProductsModel
import com.kocerlabs.onlineshopmvvm.data.network.model.SliderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    val TAG = "MainViewModel"

    private val _banner = MutableLiveData<List<SliderModel>>()
    val banner: LiveData<List<SliderModel>>
        get() = _banner

    private val _category = MutableLiveData<List<CategoryModel>>()
    val category: LiveData<List<CategoryModel>>
        get() = _category

    private val _recommendation = MutableLiveData<List<ProductsModel>>()
    val recommendation: LiveData<List<ProductsModel>>
        get() = _recommendation


    fun loadBanners() {
        val Ref = repository.loadBanners()
        // https://onlineshopmvvm-2d767-default-rtdb.europe-west1.firebasedatabase.app/Banner

        Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(SliderModel::class.java)
                    // [SliderModel(url=https://firebasestorage.googleapis.com/v0/b/project-200-1.appspot.com/o/banner1.png?alt=media&token=f575274a-95c5-482f-be80-5ef8d8496bed), SliderModel(url=https://firebasestorage.googleapis.com/v0/b/project-200-1.appspot.com/o/banner2.png?alt=media&token=4b32b621-dab3-42f5-b781-3fc4038d1101)]
                    list?.let {
                        lists.add(list)
                    }
                }
                _banner.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    fun loadCategories() {
        val Ref = repository.loadCategories()
        // https://onlineshopmvvm-2d767-default-rtdb.europe-west1.firebasedatabase.app/Category
        Log.d(TAG, "REF: $Ref")
        Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<CategoryModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(CategoryModel::class.java)
//                    list: CategoryModel(title=Pc, id=0, picUrl=https://firebasestorage.googleapis.com/v0/b/project-200-1.appspot.com/o/cat1.png?alt=media&token=e3988db7-b935-495a-abbb-89a1b0aa5e0e)
//                    list: CategoryModel(title=Smartphone, id=1, picUrl=https://firebasestorage.googleapis.com/v0/b/project-200-1.appspot.com/o/cat2.png?alt=media&token=4508a8d0-dc76-4e54-8234-4a7ddcfbfe41)
//                    list: CategoryModel(title=Headphone, id=2, picUrl=https://firebasestorage.googleapis.com/v0/b/project-200-1.appspot.com/o/cat3.png?alt=media&token=9e2e8b2c-09f8-453f-9dcd-2d83c52acd93)
                    Log.d(TAG, "list: $list")
                    list?.let {
                        lists.add(list)
                    }
                }
                _category.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    fun loadRecommended() {
        val Ref = repository.loadRecommended()
        // https://onlineshopmvvm-2d767-default-rtdb.europe-west1.firebasedatabase.app/Items
        val query: Query = Ref.orderByChild("showRecommended").equalTo(true)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ProductsModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ProductsModel::class.java)
                    Log.d(TAG, "list: $list")
                    list?.let {
                        lists.add(list)
                    }
                }
                _recommendation.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }


    fun loadFiltered(id: String) {
        val Ref = repository.loadRecommended()
        // https://onlineshopmvvm-2d767-default-rtdb.europe-west1.firebasedatabase.app/Items
        val query: Query = Ref.orderByChild("categoryId").equalTo(id.toDouble())
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ProductsModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ProductsModel::class.java)
                    Log.d(TAG, "list: $list")
                    list?.let {
                        lists.add(list)
                    }
                }
                Log.d(TAG, "Liste geldi: $lists")
                _recommendation.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
}