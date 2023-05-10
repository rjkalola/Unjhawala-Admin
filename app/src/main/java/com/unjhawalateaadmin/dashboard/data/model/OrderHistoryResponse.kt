package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse


class OrderHistoryResponse(
    var pending_count: Int = 0,
    var offset: Int = 0,
    var Data: MutableList<OrderInfo>,
) :  BaseResponse()
