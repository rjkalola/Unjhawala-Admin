package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

class DeleteOrderItemResponse : BaseResponse() {
    var cart_total: Int = 0
    var final_total: String = ""
    var total_kg: String = ""
    var total_items: Int = 0
}
