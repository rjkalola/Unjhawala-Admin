package com.unjhawalateaadmin.dashboard.data.model

import android.os.Parcelable
import com.unjhawalateaadmin.common.data.model.BaseResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
class MemberDetailsResponse(
    var firm_name: String = "",
    var firm_type_name: String = "",
    var first_name: String = "",
    var middle_name: String = "",
    var last_name: String = "",
    var registered_mobile_number: String = "",
    var primary_number: String = "",
    var secondary_number: String = "",
    var other_number: String = "",
    var birth_date: String = "",
    var anniversary_date: String = "",
    var address: String = "",
    var gst_number: String = "",
    var fssai_number: String = "",
    var bank_name: String = "",
    var account_number: String = "",
    var account_name: String = "",
    var ifsc: String = "",
    var security_cheque: Boolean = false,
    var security_cheque_photo: String = "",
    var security_cheque_bank_name: String = "",
    var security_cheque_number: String = "",
    var security_amount: String = "",
    var security_interest_rate: String = "",
    var cash_discount: String = "",
    var credit_limit_amount: String = "",
    var credit_days: String = "",
    var late_payment_interest: String = "",
    var exclusivity: String = "",
    var agencies: MutableList<AvailableAgencyInfo>,
    var areas: MutableList<CoveredAreaInfo>,

    var aadhars: MutableList<AttachmentInfo>,
    var pancards: MutableList<AttachmentInfo>,
    var gst: MutableList<AttachmentInfo>,
    var fssai: MutableList<AttachmentInfo>

) : Parcelable, BaseResponse()
