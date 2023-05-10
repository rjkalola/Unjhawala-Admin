package com.unjhawalateaadmin.common.callback

import com.unjhawalateaadmin.common.data.model.PostCodeInfo

interface SelectPostCodeListener {
    fun onSelectPostCode(info: PostCodeInfo, tag: Int)
}