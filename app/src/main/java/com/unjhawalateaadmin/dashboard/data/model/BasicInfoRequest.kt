package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class BasicInfoRequest(
    var first_name: String = "",
    var middle_name: String = "",
    var last_name: String = "",
    var primary_number: String = "",
    var secondary_number: String = "",
    var other_number: String = "",
    var birth_date: String = "",
    var anniversary_date: String = ""
): Parcelable