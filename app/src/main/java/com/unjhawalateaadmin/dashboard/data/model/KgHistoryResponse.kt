package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

class KgHistoryResponse : BaseResponse() {
    lateinit var Data: MutableList<KgHistoryInfo>
    var offset = 0
}
