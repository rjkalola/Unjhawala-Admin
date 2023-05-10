package com.imateplus.utilities.callback

import android.widget.TimePicker

interface OnTimeSetCallback {
    fun onTimeSet(view: TimePicker?, hour: Int, minute: Int)
}