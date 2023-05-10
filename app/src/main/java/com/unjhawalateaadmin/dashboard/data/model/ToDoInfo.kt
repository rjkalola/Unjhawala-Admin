package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ToDoInfo(
    var id: String = "",
    var _id: String = "",
    var member_id: String = "",
    var title: String = "",
    var description: String = "",
    var reminder_date: String = "",
    var reminder_time: String = "",
    var updated_at: String = "",
    var created_at: String = ""
) : Parcelable