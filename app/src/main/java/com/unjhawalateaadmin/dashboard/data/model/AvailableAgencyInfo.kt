package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AvailableAgencyInfo(
    var _id: String = "",
    var member_id: String = "",
    var company: String = "",
    var product: String = "",
    var turn_over: String = "",
    var updated_at: String = "",
    var created_at: String = "",
    var id: String = ""
) : Parcelable