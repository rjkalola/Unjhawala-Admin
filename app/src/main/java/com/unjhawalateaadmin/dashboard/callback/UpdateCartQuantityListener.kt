package com.unjhawalateaadmin.dashboard.callback

interface UpdateCartQuantityListener {
    fun onUpdateCartQuantity(position: Int, parentPosition: Int, action: Int)
}