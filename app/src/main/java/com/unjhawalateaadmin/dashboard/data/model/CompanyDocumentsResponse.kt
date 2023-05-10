package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

class CompanyDocumentsResponse : BaseResponse() {
    lateinit var Data: MutableList<AttachmentInfo>
}
