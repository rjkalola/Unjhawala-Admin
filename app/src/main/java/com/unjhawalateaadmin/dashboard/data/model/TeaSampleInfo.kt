package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TeaSampleInfo(
    var _id: String? = "",
    var financial_year_id: String? = "",
    var created_date: String? = "",
    var vendor_id: String? = "",
    var lu_garden_id: String? = "",
    var lu_tea_grade_id: String? = "",
    var invoice_number: String? = "",
    var bag: String? = "",
    var weight: String? = "",
    var rate: String? = "",
    var total_quantity: String? = "",
    var note: String? = "",
    var is_bill_tea_generated: Int = 0,
    var is_archive: Int = 0,
    var updated_at: String? = "",
    var created_at: String? = "",
    var show_created_date: String? = "",
    var view_rate: String? = "",
    var view_total_quantity: String? = "",
    var vendor_name: String? = "",
    var group_name: String? = "",
    var garden_name: String? = "",
    var tea_grade_name: String? = "",
    var display_name: String? = "",
): Parcelable