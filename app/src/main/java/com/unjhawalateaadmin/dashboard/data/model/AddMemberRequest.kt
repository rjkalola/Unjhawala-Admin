package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

class AddMemberRequest : BaseResponse() {
    var firm_type_id: Int = 0
    var firm_name: String = ""
    var first_name: String = ""
    var middle_name: String = ""
    var last_name: String = ""
    var registered_mobile_number: String = ""
    var primary_number: String = ""
    var secondary_number: String = ""
    var other_number: String = ""
    var lu_country_id: String = ""
    var lu_state_id: String = ""
    var lu_district_id: String = ""
    var lu_taluka_id: String = ""
    var lu_city_id: String = ""
    var lu_area_id: String = ""
    var landmark: String = ""
    var pincode: String = ""
    var address: String = ""
    var gst_number: String = ""
    var fssai_number: String = ""
    var joining_date: String = ""
    var direct_company: Int = 0
}
