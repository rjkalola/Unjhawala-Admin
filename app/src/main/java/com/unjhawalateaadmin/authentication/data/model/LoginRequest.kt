package com.unjhawalateaadmin.authentication.data.model

data class LoginRequest(
    var username: String = "",
    var password: String = "",
    var type: Int = 0
)