package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
class TeaSampleInfo(
    var _id: String? = "",
    var id: String? = "",
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
    var lu_leaf_type_id: String? = "",
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

    var lu_tea_colour_id: String? = "",
    var lu_tea_cutting_id: String? = "",
    var lu_tea_density_id: String? = "",
    var lu_tea_personal_grade_id: String? = "",
    var lu_tea_product_preference_id: String? = "",
    var lu_tea_season_detail_id: String? = "",
    var lu_tea_source_level_1_id: String? = "",
    var lu_tea_source_level_2_id: String? = "",
    var lu_tea_source_level_3_id: String? = "",
    var manufacturer_date: String? = "",
    var our_quality_id: String? = "",

    var testing_added_by: String? = "",
    var show_manufacturer_date: String? = "",
    var personal_grade_name: String? = "",
    var season_detail_name: String? = "",
    var tea_colour_name: String? = "",
    var tea_cutting_name: String? = "",
    var tea_density_name: String? = "",
    var tea_preference_name: String? = "",
    var tea_source_level1_name: String? = "",
    var tea_source_level2_name: String? = "",
    var tea_source_level3_name: String? = "",
    var tea_type_name: String? = "",
    var tea_personal_grade_name: String? = "",

    var grade_name: String? = "",
    var colour_name: String? = "",
    var type_name: String? = "",
    var p_grade_name: String? = "",
    var season_quality_detail_name: String? = "",
    var attachment: String? = "",
    var our_quality_name: String? = "",
    var rating: String? = "",
    var density_name: String? = "",
    var preference_name: String? = "",

    var details: MutableList<ModuleInfo> = ArrayList()


) : Parcelable