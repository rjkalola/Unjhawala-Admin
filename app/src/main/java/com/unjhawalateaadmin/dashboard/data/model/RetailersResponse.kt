package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

class RetailersResponse : BaseResponse() {
    var offset: Int = 0
    var pending_count: Int = 0
    lateinit var Data: MutableList<RetailerInfo>
}
