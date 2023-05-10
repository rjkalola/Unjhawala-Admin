package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CartProductInfo(
    var data: MutableList<CartProductItemsInfo>,
    var items_count: String = "",
    var name: String = ""
) : Parcelable
