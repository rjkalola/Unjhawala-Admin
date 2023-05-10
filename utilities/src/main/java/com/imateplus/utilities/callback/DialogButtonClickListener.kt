package com.imateplus.utilities.callback

interface DialogButtonClickListener {
    fun onPositiveButtonClicked(dialogIdentifier: Int)
    fun onNegativeButtonClicked(dialogIdentifier: Int)
}