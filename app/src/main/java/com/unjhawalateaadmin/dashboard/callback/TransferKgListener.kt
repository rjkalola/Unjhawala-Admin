package com.unjhawalateaadmin.dashboard.callback

interface TransferKgListener {
    fun onSubmitKg(toMemberId: String, kg: String)
}