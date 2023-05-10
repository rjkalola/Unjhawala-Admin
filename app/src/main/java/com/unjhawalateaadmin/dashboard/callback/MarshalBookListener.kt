package com.unjhawalateaadmin.dashboard.callback

import com.unjhawalateaadmin.dashboard.data.model.OrderInfo

interface MarshalBookListener {
    fun viewOrder(orderInfo: OrderInfo)
}