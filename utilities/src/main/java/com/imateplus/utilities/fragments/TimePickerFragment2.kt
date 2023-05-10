package com.imateplus.utilities.fragments

import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.imateplus.utilities.callback.OnTimeSetCallback
import com.imateplus.utilities.utils.Constant
import com.imateplus.utilities.utils.DateHelper
import com.imateplus.utilities.utils.StringHelper
import java.util.*

class TimePickerFragment2 : DialogFragment(),TimePickerDialog.OnTimeSetListener{
    var onTimeSetCallback: OnTimeSetCallback? = null
    var identifier: String? = null

    companion object {
        fun newInstance(
            identifier: String?,
            time: String?,
            timeFormat: String?,
            is24HourView: Boolean
        ): TimePickerFragment2? {
            val args = Bundle()
            args.putString(Constant.IntentKey.TIME_PICKER_IDENTIFIER, identifier)
            args.putString(Constant.IntentKey.TIME, time)
            args.putString(Constant.IntentKey.TIME_FORMAT, timeFormat)
            args.putBoolean(Constant.IntentKey.IS_24_HOUR_VIEW, is24HourView)
            val fragment = TimePickerFragment2()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            onTimeSetCallback = try {
                context as OnTimeSetCallback
            } catch (e: ClassCastException) {
                throw ClassCastException(
                    context.toString()
                            + " must implement MyInterface "
                )
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        if (bundle != null) {
            identifier = bundle.getString(Constant.IntentKey.TIME_PICKER_IDENTIFIER, "")
            val timeStr = bundle.getString(Constant.IntentKey.TIME, "")
            val timeFormat = bundle.getString(Constant.IntentKey.TIME_FORMAT, "")
            val is24HourView = bundle.getBoolean(Constant.IntentKey.IS_24_HOUR_VIEW, false)
            val c = Calendar.getInstance()
            if (!StringHelper.isEmpty(timeStr) && !StringHelper.isEmpty(timeFormat)) {
                try {
                    c.time = DateHelper.stringToDate(timeStr, timeFormat)
                } catch (e: Exception) {
                    Log.e(
                        this@TimePickerFragment2.javaClass.simpleName,
                        "error in onCreateDialog(): " + e.message
                    )
                }
            }
            val hour = c[Calendar.HOUR_OF_DAY]
            val minute = c[Calendar.MINUTE]
            val dialog = TimePickerDialog(activity, this, hour, minute, is24HourView)
            dialog.setOnShowListener {
                dialog.getButton(Dialog.BUTTON_NEGATIVE).visibility = View.GONE;
            }
            return dialog
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        view.tag = identifier
        onTimeSetCallback!!.onTimeSet(view, hourOfDay, minute)
    }
}