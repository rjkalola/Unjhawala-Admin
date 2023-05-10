package com.unjhawalateaadmin.dashboard.data.model

import com.unjhawalateaadmin.common.data.model.BaseResponse

class UsersResponse : BaseResponse() {
    var offset: Int = 0
    lateinit var Data: MutableList<UserInfo>
}
