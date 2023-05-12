package com.unjhawalateaadmin.common.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ModuleInfo(
    var id: Int = 0,
    var _id: String = "",
    var position: Int = 0,
    var name: String = "",
    var value: String = "",
    var count: Int = 0,
    var quantity: Int = 0,
    var data: MutableList<ModuleInfo> = ArrayList(),
    var lu_country_id: String = "",
    var lu_state_id: String = "",
    var lu_district_id: String = "",
    var lu_taluka_id: String = "",
    var lu_city_id: String = "",
    var pincode: String = "",
    var check: Boolean = false,
) : Parcelable
