package com.unjhawalateaadmin.common.data.model

class SwipeItemInfo {
    var id: Int = 0
    var name: String = ""

    fun copyData(info: SwipeItemInfo){
        this.id = info.id
        this.name = info.name
    }
}
