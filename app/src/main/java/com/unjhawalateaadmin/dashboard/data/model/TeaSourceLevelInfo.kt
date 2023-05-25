package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TeaSourceLevelInfo(
    var _id: String? = "",
    var id: String? = "",
    var level_1: String? = "",
    var level_2: String? = "",
    var level_1_name: String? = "",
    var level_2_name: String? = "",
    var name: String? = "",
    var lower_name: String? = "",
    var updated_at: String? = "",
    var created_at: String? = "",
    var first_levels: MutableList<TeaSourceLevelInfo> = ArrayList(),
    var second_levels: MutableList<TeaSourceLevelInfo> = ArrayList()
) : Parcelable