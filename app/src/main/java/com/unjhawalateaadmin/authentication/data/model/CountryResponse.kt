package com.unjhawalateaadmin.authentication.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.data.model.ModuleInfo

class CountryResponse : BaseResponse() {
    lateinit var data: MutableList<ModuleInfo>
}
