package com.unjhawalateaadmin.authentication.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SignUpRequest(
    var name: String = "",
    var first_name: String = "",
    var last_name: String = "",
    var email: String = "",
    var password: String = "",
    var confirm_password: String = "",
    var phone_extension_id: String = "",
    var country_id: Int = 0,
    var phone_number: String = "",
    var street_address: String = "",
    var address: String = "",
    var city: String = "",
    var state: String = "",
    var city_id: Int = 0,
    var state_id: Int = 0,
    var company_name: String = "",
    var pin_code: String = "",
    var device_type: String = "",
    var device_token: String = "",
) : Parcelable