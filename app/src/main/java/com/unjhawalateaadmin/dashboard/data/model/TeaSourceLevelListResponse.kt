package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

class TeaSourceLevelListResponse : BaseResponse() {
    lateinit var Data: MutableList<TeaSourceLevelInfo>
    var firstLevels: MutableList<TeaSourceLevelInfo> = ArrayList()
    var secondLevels: MutableList<TeaSourceLevelInfo> = ArrayList()
    var thirdLevels: MutableList<TeaSourceLevelInfo> = ArrayList()
}
