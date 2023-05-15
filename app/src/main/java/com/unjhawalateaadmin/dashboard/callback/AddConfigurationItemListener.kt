package com.unjhawalateaadmin.dashboard.callback

import com.unjhawalateaadmin.dashboard.data.model.ConfigurationItemInfo

interface AddConfigurationItemListener {
    fun onAddConfigurationItem(itemType: Int, info: ConfigurationItemInfo)
}