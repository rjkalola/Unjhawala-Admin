package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

class RatePrintResponse : BaseResponse() {
    lateinit var Data: MutableList<RatePrintInfo>
    var print_url: String = ""
}
