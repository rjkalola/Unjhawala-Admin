package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AvailableTeaSampleInfo(
    var id: String? = "",
    var show_created_date: String? = "",
    var display_name: String? = "",
    var tea_grade_name: String? = "",
    var grade_name: String? = "",
    var personal_grade_name: String? = "",
    var vendor_name: String? = "",
    var bag: String? = "",
    var weight: String? = "",
    var rate: String? = "",
    var total_quantity: String? = "",
    var note: String? = "",
    var selected:Boolean? = false

): Parcelable