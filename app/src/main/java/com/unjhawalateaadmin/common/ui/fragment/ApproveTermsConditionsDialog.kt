package com.unjhawalateaadmin.common.ui.fragment

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.dashboard.data.model.PrivacyPolicyResponse
import com.unjhawalateaadmin.databinding.DialogApproveTearmsConditionBinding


class ApproveTermsConditionsDialog(
    var mContext: Activity,
    var mListener: SelectItemListener,
    var data: PrivacyPolicyResponse
) : DialogFragment(), View.OnClickListener {
    private lateinit var binding: DialogApproveTearmsConditionBinding
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyDialogFragmentStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val ad = AlertDialog.Builder(mContext)
        val i = mContext.layoutInflater
        val view = i.inflate(R.layout.dialog_approve_tearms_condition, null)
        binding = DataBindingUtil.bind(view)!!
        ad.setView(view)
        dialog = ad.create()

        binding.txtOk.setOnClickListener(this)

        binding.txtPrivacyPolicy.text =
            Html.fromHtml(data.Data, Html.FROM_HTML_MODE_COMPACT)

        return dialog
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txtOk -> {
                mListener.onSelectItem(0, AppConstants.Action.APPROVE_TERMS_CONDITIONS, 0)
                dismiss()
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}