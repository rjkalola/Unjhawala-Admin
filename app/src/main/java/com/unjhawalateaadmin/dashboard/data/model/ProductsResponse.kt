package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

class ProductsResponse : BaseResponse() {
    lateinit var Data: MutableList<ProductInfo>
    var cart_total: Int = 0
    var final_total: String = ""
    var total_kg: String = ""
}
