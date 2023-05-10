package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CoveredAreaInfo(
    var _id: String = "",
    var area_city_taluka_name: String = "",
    var district_state_name: String = ""
) : Parcelable