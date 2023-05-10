package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import java.time.temporal.TemporalAmount

class MarshalBookResponse : BaseResponse() {
    lateinit var Data: MutableList<MarshalBookData>
    lateinit var amount:String
    lateinit var pending_kg:String
}
