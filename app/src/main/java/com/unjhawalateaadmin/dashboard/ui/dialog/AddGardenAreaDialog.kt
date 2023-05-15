package com.unjhawalateaadmin.dashboard.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.imateplus.utilities.utils.StringHelper
import com.imateplus.utilities.utils.ValidationUtil
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.dashboard.callback.AddConfigurationItemListener
import com.unjhawalateaadmin.dashboard.data.model.ConfigurationItemInfo
import com.unjhawalateaadmin.databinding.DialogAddGardenAreaBinding

class AddGardenAreaDialog(mContext: Context?) :
    BottomSheetDialog(mContext!!, R.style.CustomBottomSheetDialogTheme) {
    private lateinit var binding: DialogAddGardenAreaBinding

    companion
    object {
        private var mListener: AddConfigurationItemListener? = null
        private lateinit var mContext: Context
        private var itemType: Int = 0
        private var itemName: String = ""
        private var mConfigurationItemInfo: ConfigurationItemInfo? = null

        fun newInstance(
            context: Context?,
            info: ConfigurationItemInfo?,
            itemType: Int,
            itemName: String,
            listener: AddConfigurationItemListener
        ): AddGardenAreaDialog {
            this.mListener = listener
            this.mContext = context!!
            this.itemType = itemType
            this.itemName = itemName
            if (info != null)
                this.mConfigurationItemInfo = info
            else
                this.mConfigurationItemInfo = ConfigurationItemInfo()
            return AddGardenAreaDialog(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sheetView: View = layoutInflater.inflate(R.layout.dialog_add_garden_area, null)
        setContentView(sheetView)
        binding = DataBindingUtil.bind(sheetView)!!
        binding.info = mConfigurationItemInfo

        if(!StringHelper.isEmpty(mConfigurationItemInfo?.id))
            binding.txtTitle.text = "Edit $itemName"
        else
            binding.txtTitle.text = "Add $itemName"

        binding.switchStatus.isChecked = mConfigurationItemInfo?.status != 0

        binding.switchStatus.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                if (isChecked)
                    mConfigurationItemInfo?.status = 1
                else
                    mConfigurationItemInfo?.status = 0
            }
        }

        binding.txtSave.setOnClickListener {
            if (!ValidationUtil.isEmptyEditText(binding.edtName.text.toString().trim())) {
                binding.layoutName.error = null
                binding.layoutName.isErrorEnabled = false
                mListener?.onAddConfigurationItem(itemType, mConfigurationItemInfo!!)
                dismiss()
            } else {
                ValidationUtil.setErrorIntoInputTextLayout(
                    binding.edtName, binding.layoutName,
                    mContext.getString(R.string.empty_edittext_error)
                )
            }
        }

        binding.imgClose.setOnClickListener { dismiss() }

    }

    override fun onStart() {
        super.onStart()
//        this.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
//        this.window!!.setBackgroundDrawableResource(android.R.color.transparent);
        this.window!!.setLayout(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }

    fun BottomSheetDialogFragment.setTransparentBackground() {
        dialog?.apply {
            setOnShowListener {
                val bottomSheet = findViewById<View?>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet?.setBackgroundResource(android.R.color.transparent)
            }
        }
    }
}