package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class OrderInfo(
    var created_date: String = "",
    var created_time: String = "",
    var amount: String = "",
    var note: String = "",
    var type: Int = 0,
    var side: String = "",
    var image: String = "",

    var order_id: String = "",
    var id: String = "",
    var invoice_number: String = "",
    var created_date_time: String = "",
    var total_amount: String = "",
    var total_items: String = "",
    var total_kg: String = "",
    var firm_name: String = "",
    var member_name: String = "",
    var registered_mobile_number: String = "",
    var city: String = "",
    var cart_total: Int = 0,

    var details: MutableList<CartProductInfo> = ArrayList()
): Parcelable