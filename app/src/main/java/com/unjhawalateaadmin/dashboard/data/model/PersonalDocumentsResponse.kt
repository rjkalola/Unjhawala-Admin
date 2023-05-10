package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

class PersonalDocumentsResponse : BaseResponse() {
    lateinit var aadhars: MutableList<AttachmentInfo>
    lateinit var pancards: MutableList<AttachmentInfo>
    lateinit var fssai: MutableList<AttachmentInfo>
    lateinit var gst: MutableList<AttachmentInfo>
}
