package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

class TeaTestedSampleListResponse : BaseResponse() {
    lateinit var Data: MutableList<TeaSampleTestingInfo>
    var offset:Int = 0
}
