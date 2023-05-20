package com.unjhawalateaadmin.dashboard.callback

import com.unjhawalateaadmin.dashboard.data.model.AvailableTeaSampleInfo

interface AddAvailableTeaSampleListener {
    fun onAddAvailableTeaSample(info: AvailableTeaSampleInfo)
}