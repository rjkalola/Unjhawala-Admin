package com.unjhawalateaadmin.authentication.data.model

data class User(
    var id: String = "",
    var member_type_id: Int = 0,
    var name: String = "",
    var username: String = "",
    var email: String = "",
    var phone_number: String = "",
    var image: String = "",
    var is_mpin: Int = 0,
    var api_token: String = "",
    var total_kg: Double = 0.0,
    var first_letter: String = "",
    var accept_terms: Int = 0,
    var joining_date: String = ""
)