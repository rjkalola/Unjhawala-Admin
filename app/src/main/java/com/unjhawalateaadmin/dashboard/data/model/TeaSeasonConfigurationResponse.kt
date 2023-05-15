package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.data.model.ModuleInfo

class TeaSeasonConfigurationResponse : BaseResponse() {
    lateinit var Data: MutableList<ModuleInfo>
}
