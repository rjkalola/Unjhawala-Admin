package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProductInfo(
    var name: String = "",
    var image: String = "",
    var data: MutableList<ProductComponentInfo>  = ArrayList(),
) : Parcelable