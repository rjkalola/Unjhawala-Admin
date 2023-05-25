package com.unjhawalateaadmin.dashboard.callback

import com.unjhawalateaadmin.common.data.model.ModuleInfo

interface OnApplyFilterListener {
    fun onApplyFilter(filters: MutableList<ModuleInfo>)
}