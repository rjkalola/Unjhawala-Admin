package com.unjhawalateaadmin.dashboard.callback

import com.unjhawalateaadmin.dashboard.data.model.WorkingAreaListResponse

interface AgencyDealerFilterListener {
    fun onApplyFilter(data: WorkingAreaListResponse)
}