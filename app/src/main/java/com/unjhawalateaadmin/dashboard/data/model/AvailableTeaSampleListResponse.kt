package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import com.unjhawalateaadmin.common.data.model.BaseResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
class AvailableTeaSampleListResponse(
    var Data: MutableList<AvailableTeaSampleInfo> = ArrayList(),
    var offset: Int = 0
) : BaseResponse(), Parcelable
