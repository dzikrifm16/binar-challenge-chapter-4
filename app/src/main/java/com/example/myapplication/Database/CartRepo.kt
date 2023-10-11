package com.example.myapplication.Database

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CartRepo(application: Application) {

    private val _cartDao: CartDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db= CartDatabase.getInstance(application)
        _cartDao = db.cartDao
    }

    fun insert(cart: Cart) {
        executorService.execute { _cartDao.insert(cart) }
    }

    fun getAllCartItems(): LiveData<List<Cart>> = _cartDao.getAllItem()

    fun deleteItemCart(cartId: Long) {
        executorService.execute{
            _cartDao.deleteItemById(cartId)}
    }

    fun updateQuantityItem(cart : Cart) {
        executorService.execute { _cartDao.update(cart) }
    }
}