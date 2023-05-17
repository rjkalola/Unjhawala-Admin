package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class TeaSampleTestingInfo(
    var id: String? = "",
    var lu_tea_personal_grade_id: String? = "",
    var lu_tea_colour_id: String? = "",
    var lu_tea_cutting_id: String? = "",
    var lu_tea_density_id: String? = "",
    var lu_tea_season_detail_id: String = "",
    var lu_tea_product_preference_id: String? = "",
    var lu_tea_source_level_1_id: String? = "",
    var lu_tea_source_level_2_id: String? = "",
    var lu_tea_source_level_3_id: String? = "",
    var our_quality_id: String? = "",
    var manufacturer_date: String? = "",
    var file: String? = "",
    var note: String? = "",
): Parcelable