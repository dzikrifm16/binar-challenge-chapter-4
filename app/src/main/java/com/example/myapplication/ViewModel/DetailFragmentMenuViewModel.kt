package com.example.myapplication.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Database.Cart
import com.example.myapplication.Database.CartRepo
import com.example.myapplication.DataClass.ItemMenu

class DetailFragmentMenuViewModel(application: Application) : ViewModel() {

    private val _counter = MutableLiveData(1)
    val counter: LiveData<Int> = _counter

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> = _totalPrice

    private val _selectedItem = MutableLiveData<ItemMenu>()

    private val cartRepo: CartRepo

    init {
        cartRepo = CartRepo(application)
    }

    private fun insert(cart: Cart) {
        cartRepo.insert(cart)
    }


    fun initSelectedItem(item: ItemMenu) {
        _selectedItem.value = item
        _totalPrice.value = item.price

    }

    private fun total() {
        val currentAmount = _counter.value ?: 1
        val selectedItem = _selectedItem.value
        if (selectedItem != null) {
            val totalPrice = selectedItem.price * currentAmount
            _totalPrice.value = totalPrice
        }

    }

    fun increment() {
        _counter.value = (_counter.value ?: 1) + 1
        total()
    }


    fun decrement() {
        val currentValues: Int = _counter.value ?: 1
        if (currentValues > 1) {
            _counter.value = currentValues - 1
            total()
        }
    }

    fun addToCart() {
        val selectedItem = _selectedItem.value

        selectedItem?.let {
            val cartItem =
                totalPrice.value?.let { it1 ->
                    counter.value?.let { it2 ->
                        Cart(
                            itemName = it.name,
                            imgId = it.images,
                            priceMenu = it.price,
                            itemQuantity = it2,
                            totalPrice = it1,
                            itemNote = ""
                        )
                    }
                }
            cartItem?.let { it1 -> insert(it1) }
        }
    }

}



