package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.data.model.ModuleInfo

class MemberConfigurationResponse : BaseResponse() {
    lateinit var firm_types: MutableList<ModuleInfo>
    lateinit var countries: MutableList<ModuleInfo>
    lateinit var states: MutableList<ModuleInfo>
    lateinit var districts: MutableList<ModuleInfo>
    lateinit var talukas: MutableList<ModuleInfo>
    lateinit var cities: MutableList<ModuleInfo>
    lateinit var areas: MutableList<ModuleInfo>
}
