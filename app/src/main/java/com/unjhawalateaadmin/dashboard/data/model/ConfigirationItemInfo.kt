package com.unjhawalateaadmin.dashboard.data.model


class ConfigurationItemInfo {
    var id: String = ""
    var name: String = ""
    var position: Int = 0
    var status: Int = 0
    var lower_name: String = ""
    var lu_leaf_type_id: String = ""
    var lu_garden_area_id: String = ""
    var leaf_type_name: String = ""
    var area_name: String = ""
    var tea_season_quality: MutableList<TeaSeasonQualityInfo> = ArrayList()
}
