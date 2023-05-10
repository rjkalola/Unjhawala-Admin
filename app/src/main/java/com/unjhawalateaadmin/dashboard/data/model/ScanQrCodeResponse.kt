package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import com.unjhawalateaadmin.common.data.model.BaseResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
class ScanQrCodeResponse(
    var final_kg: String = "",
    var total_kg: String = "",
    var invoice_number: String = "",
    var created_date_time: String = "",
    var order_id: String = "",
) : Parcelable, BaseResponse()
