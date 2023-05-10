package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CartProductItemsInfo(
    var _id: String = "",
    var member_id: String = "",
    var product_sku_id: String = "",
    var quantity: Int = 0,
    var cart_qty: Int = 0,
    var order_type: Int = 0,
    var price: String = "",
    var updated_at: String = "",
    var created_at: String = "",
    var image: String = "",
    var name: String = "",
    var product_name: String = "",
    var variant: String = "",
    var code: String = "",
    var id: String = "",
    var total: Int = 0,
    var sub_total: Int = 0,
) : Parcelable