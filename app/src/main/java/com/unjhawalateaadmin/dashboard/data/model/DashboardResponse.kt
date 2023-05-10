package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

class DashboardResponse : BaseResponse() {
    var pending_order: Int = 0
    var dealer_pending_order: Int = 0
    var total_kg: Double = 0.0
    var available_kg: String = ""
    var current_year_kg: String = ""
    var previous_year_kg: String = ""
    var first_letter: String = ""
    var accept_terms: Int = 0
    var joining_date: String = ""
    var app_version: Int = 0
}
