package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AttachmentInfo(
    var _id: String = "",
    var member_id: String = "",
    var document_type: String = "",
    var document_extension: String = "",
    var attachment: String = "",
    var attachment_type: String = "",
    var updated_at: String = "",
    var created_at: String = "",
    var title: String = "",
) : Parcelable