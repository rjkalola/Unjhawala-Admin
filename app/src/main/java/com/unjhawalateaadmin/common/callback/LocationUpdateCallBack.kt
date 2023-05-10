package com.unjhawalateaadmin.common.callback

import android.location.Location

interface LocationUpdateCallBack {
    fun locationUpdate(location: Location)
}