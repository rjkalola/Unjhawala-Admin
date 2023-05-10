package com.unjhawalateaadmin.dashboard.callback

interface SelectAttachmentItemListener {
    fun onSelectAttachment(position: Int, action: Int,attachmentPath: String)
}