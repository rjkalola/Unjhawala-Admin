package com.unjhawalateaadmin.dashboard.callback

interface UpdateDeliverItemsListener {
    fun onUpdateCartItem(
        position: Int,
        parentPosition: Int,
        quantity: Int,
        price: String
    )
}