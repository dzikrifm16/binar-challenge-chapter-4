package com.example.myapplication.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.DataClass.ItemMenu

class HomeViewModel: ViewModel (){
    val isListView = MutableLiveData<Boolean>().apply { value = true }
    val menuItem = MutableLiveData<ArrayList<ItemMenu>>()
}