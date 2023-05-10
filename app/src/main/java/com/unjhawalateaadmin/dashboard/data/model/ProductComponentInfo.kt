package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProductComponentInfo(
    var id: String = "",
    var code: String = "",
    var product_type: String = "",
    var variant: String = "",
    var is_inner: Int = 0,
    var weight_in_one_inner: String = "",
    var numbers_in_one_inner: String = "",
    var weight_in_one_outer: String = "",
    var numbers_in_one_outer: String = "",
    var numbers_in_one_outer_packages: String = "",
    var package_type_id: Int = 0,
    var price: String = "",
    var price_label: String = "",
    var weight: String = "",
    var one_outer_kg: String = "",
    var cart_qty: Int = 0,
    var product_info: MutableList<ModuleInfo> = ArrayList()
) : Parcelable