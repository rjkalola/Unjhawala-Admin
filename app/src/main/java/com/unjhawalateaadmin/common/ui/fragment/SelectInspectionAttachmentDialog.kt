package com.unjhawalateaadmin.common.ui.fragment

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectAttachmentListener
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.databinding.DialogSelectInspectionAttachmentBinding

class SelectInspectionAttachmentDialog(
    var mContext: Activity,
    var mListener: SelectAttachmentListener,
) : DialogFragment(), View.OnClickListener {
    private lateinit var binding: DialogSelectInspectionAttachmentBinding
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyDialogFragmentStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val ad = AlertDialog.Builder(mContext)
        val i = mContext.layoutInflater
        val view = i.inflate(R.layout.dialog_select_inspection_attachment, null)
        binding = DataBindingUtil.bind(view)!!
        ad.setView(view)
        dialog = ad.create()


        binding.routCamera.setOnClickListener(this)
        binding.routPhotos.setOnClickListener(this)
        binding.routVideo.setOnClickListener(this)

        return dialog
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.routCamera -> mListener.onSelectAttachment(AppConstants.Type.SELECT_FROM_CAMERA)
            R.id.routPhotos -> mListener.onSelectAttachment(AppConstants.Type.SELECT_PHOTOS)
            R.id.routVideo -> mListener.onSelectAttachment(AppConstants.Type.SELECT_VIDEO_FROM_CAMERA)
        }
        dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}