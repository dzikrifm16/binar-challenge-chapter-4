package com.example.myapplication.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Database.Cart
import com.example.myapplication.Database.CartRepo

class CartViewModel(application: Application) : ViewModel() {
	val repository: CartRepo = CartRepo(application)

	val allCartItems: LiveData<List<Cart>> = repository.getAllCartItems()

	fun deleteItemCart(cartId: Long) {
		repository.deleteItemCart(cartId)
	}

	fun updateQuantityItem(cart: Cart) {
		repository.updateQuantityItem(cart)
	}

	fun increment(cart: Cart) {
		val newTotal = cart.itemQuantity + 1
		cart.itemQuantity = newTotal

		cart.totalPrice = cart.priceMenu * newTotal

        updateQuantityItem(cart)
	}


	fun decrement(cart: Cart) {
		val newTotal = cart.itemQuantity - 1
		cart.itemQuantity = newTotal

		cart.totalPrice = cart.priceMenu * newTotal

        updateQuantityItem(cart)
	}


}
