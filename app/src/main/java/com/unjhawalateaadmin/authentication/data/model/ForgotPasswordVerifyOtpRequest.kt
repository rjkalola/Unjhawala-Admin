package com.unjhawalateaadmin.authentication.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

data class ForgotPasswordVerifyOtpRequest(
    var email: String = "",
    var otp: String = "",
    var guard: String = "",
) : BaseResponse()
