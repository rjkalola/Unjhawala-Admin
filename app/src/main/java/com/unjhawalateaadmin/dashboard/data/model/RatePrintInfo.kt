package com.unjhawalateaadmin.dashboard.data.model

class RatePrintInfo {
    var _id: String = ""
    var name: String = ""
    var lower_name: String = ""
    var position: Int = 0
    var status: Int = 0
    var updated_at: String = ""
    var created_at: String = ""
    var color_code: String = ""
    lateinit var products: MutableList<RatePrintProductInfo>
}
