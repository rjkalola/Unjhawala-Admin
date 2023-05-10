package com.unjhawalateaadmin.authentication.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

data class ForgotPasswordSendOtpRequest(
    var mobile: String = "",
    var mobileWithExtension: String = "",
    var email: String = "",
    var option: String = "",
    var guard: String = ""
) : BaseResponse()
