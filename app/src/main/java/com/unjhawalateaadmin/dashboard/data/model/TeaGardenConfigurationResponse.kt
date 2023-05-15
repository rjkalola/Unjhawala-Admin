package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.data.model.ModuleInfo

class TeaGardenConfigurationResponse : BaseResponse() {
    lateinit var gardenAreas: MutableList<ModuleInfo>
    lateinit var leafTypes: MutableList<ModuleInfo>
}
