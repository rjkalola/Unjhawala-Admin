package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.data.model.ModuleInfo

class TeaSampleConfigurationResponse : BaseResponse() {
    lateinit var vendors: MutableList<ModuleInfo>
    lateinit var gardens: MutableList<ModuleInfo>
    lateinit var grades: MutableList<ModuleInfo>
    lateinit var colours: MutableList<ModuleInfo>
    lateinit var cuttings: MutableList<ModuleInfo>
    lateinit var densities: MutableList<ModuleInfo>
    lateinit var seasons: MutableList<ModuleInfo>
    lateinit var preferences: MutableList<ModuleInfo>
    lateinit var leafTypes: MutableList<ModuleInfo>
    lateinit var qualities: MutableList<ModuleInfo>
    lateinit var sources: MutableList<ModuleInfo>
}
