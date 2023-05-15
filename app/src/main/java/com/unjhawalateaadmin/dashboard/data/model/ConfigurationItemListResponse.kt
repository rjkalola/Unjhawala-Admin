package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

class ConfigurationItemListResponse : BaseResponse() {
    lateinit var Data: MutableList<ConfigurationItemInfo>
    var offset:Int = 0
}
