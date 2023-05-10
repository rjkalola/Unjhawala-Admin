package com.unjhawalateaadmin.dashboard.callback

interface AddCartQuantityListener {
    fun onAddToCart(id: String, quantity: Int, order_type: Int, price: String)
    fun cancelAddToCart()
}