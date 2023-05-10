package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse


class SupportResponse(
    var dealer_name: String = "",
    var dealer_firm_name: String = "",
    var dealer_phone_number: String = "",
    var dealer_address: String = "",
    var dealer_image: String = "",
    var salesman_name: String = "",
    var salesman_firm_name: String = "",
    var salesman_phone_number: String = "",
    var salesman_image: String = "",
) : BaseResponse()
