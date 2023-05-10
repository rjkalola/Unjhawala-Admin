package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
class WorkingAreaListResponse(
    var areas: MutableList<ModuleInfo>,
    var cities: MutableList<ModuleInfo>
) : Parcelable, BaseResponse()