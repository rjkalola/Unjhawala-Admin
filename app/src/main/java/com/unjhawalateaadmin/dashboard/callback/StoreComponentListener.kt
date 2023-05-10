package com.unjhawalateaadmin.dashboard.callback

interface StoreComponentListener {
    fun onStoreComponent(productId: Int,quantity: Int,note:String)
}