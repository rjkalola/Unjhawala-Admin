package com.unjhawalateaadmin.dashboard.callback

interface MyAccountFilterListener {
    fun onApplyFilter(filterType: Int, startDate: String, endDate: String)
}