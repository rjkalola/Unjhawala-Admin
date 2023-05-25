package com.unjhawalateaadmin.dashboard.callback

import com.unjhawalateaadmin.dashboard.data.model.TeaSourceLevelInfo

interface AddTeaSourceItemListener {
    fun onAddTeaSource(level: Int, info: TeaSourceLevelInfo)
}