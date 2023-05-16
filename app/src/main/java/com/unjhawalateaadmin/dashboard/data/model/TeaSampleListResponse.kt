package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

class TeaSampleListResponse : BaseResponse() {
    lateinit var Data: MutableList<TeaSampleInfo>
    var offset: Int = 0
}
