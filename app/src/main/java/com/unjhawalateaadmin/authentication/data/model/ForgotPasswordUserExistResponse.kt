package com.unjhawalateaadmin.authentication.data.model

import android.os.Parcelable
import com.unjhawalateaadmin.common.data.model.BaseResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
class ForgotPasswordUserExistResponse(
    var mobile: String = "",
    var mobileWithExtension: String = "",
    var mobileExtension: String = "",
    var email: String = "",
) : BaseResponse(), Parcelable
