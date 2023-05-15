package com.unjhawalateaadmin.common.utils

import com.unjhawalateaadmin.MyApplication

object VariantConfig {
    val serverBaseUrl: String
        get() {
            return MyApplication().preferenceGetString(
                AppConstants.SharedPrefKey.APP_URL,
                "http://3.111.141.93/api/v1/manager/"
            )
//            return MyApplication().preferenceGetString(
//                AppConstants.SharedPrefKey.APP_URL,
//                "http://erp.unjhawalatea.in/api/v1/"
//            )
        }
}