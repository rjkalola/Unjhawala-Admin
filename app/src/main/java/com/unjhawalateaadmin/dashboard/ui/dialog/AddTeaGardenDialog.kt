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
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.dashboard.callback.AddConfigurationItemListener
import com.unjhawalateaadmin.dashboard.data.model.ConfigurationItemInfo
import com.unjhawalateaadmin.dashboard.data.model.TeaGardenConfigurationResponse
import com.unjhawalateaadmin.databinding.DialogAddTeaGardenBinding

class AddTeaGardenDialog(mContext: Context?) :
    BottomSheetDialog(mContext!!, R.style.CustomBottomSheetDialogTheme), SelectItemListener {
    private lateinit var binding: DialogAddTeaGardenBinding

    companion
    object {
        private var mListener: AddConfigurationItemListener? = null
        private lateinit var mContext: Context
        private var itemType: Int = 0
        private var itemName: String = ""
        private var mConfigurationItemInfo: ConfigurationItemInfo? = null
        private var teaGardenConfigurationResponse: TeaGardenConfigurationResponse? = null
        private lateinit var selectItemBottomSheetDialog: SelectItemBottomSheetDialog

        fun newInstance(
            context: Context?,
            info: ConfigurationItemInfo?,
            teaGardenConfigurationResponse: TeaGardenConfigurationResponse?,
            itemType: Int,
            itemName: String,
            listener: AddConfigurationItemListener
        ): AddTeaGardenDialog {
            this.mListener = listener
            this.mContext = context!!
            this.itemType = itemType
            this.itemName = itemName
            this.teaGardenConfigurationResponse = teaGardenConfigurationResponse
            if (info != null)
                this.mConfigurationItemInfo = info
            else
                this.mConfigurationItemInfo = ConfigurationItemInfo()
            return AddTeaGardenDialog(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sheetView: View = layoutInflater.inflate(R.layout.dialog_add_tea_garden, null)
        setContentView(sheetView)
        binding = DataBindingUtil.bind(sheetView)!!
        binding.info = mConfigurationItemInfo

        if (!StringHelper.isEmpty(mConfigurationItemInfo?.id))
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
            if (valid()) {
                mListener?.onAddConfigurationItem(itemType, mConfigurationItemInfo!!)
                dismiss()
            }
        }

        binding.edtLeafType.setOnClickListener {
            showSelectItemDialog(
                teaGardenConfigurationResponse?.leafTypes!!,
                mContext.getString(R.string.select_leaf_type),
                AppConstants.DialogIdentifier.SELECT_LEAF_TYPE
            )
        }

        binding.edtArea.setOnClickListener {
            showSelectItemDialog(
                teaGardenConfigurationResponse?.gardenAreas!!,
                mContext.getString(R.string.select_area),
                AppConstants.DialogIdentifier.SELECT_AREA
            )
        }

        binding.imgClose.setOnClickListener { dismiss() }

    }

    private fun valid(): Boolean {
        var valid = true

        if (!ValidationUtil.isEmptyEditText(binding.edtLeafType.text.toString().trim())) {
            binding.layoutLeafType.error = null
            binding.layoutLeafType.isErrorEnabled = false
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtLeafType, binding.layoutLeafType,
                mContext.getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(binding.edtArea.text.toString().trim())) {
            binding.layoutArea.error = null
            binding.layoutArea.isErrorEnabled = false
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtArea, binding.layoutArea,
                mContext.getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(binding.edtName.text.toString().trim())) {
            binding.layoutName.error = null
            binding.layoutName.isErrorEnabled = false
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtName, binding.layoutName,
                mContext.getString(R.string.empty_edittext_error)
            )
            valid = false
        }
        return valid
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
                val bottomSheet =
                    findViewById<View?>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet?.setBackgroundResource(android.R.color.transparent)
            }
        }
    }

    private fun showSelectItemDialog(
        list: MutableList<ModuleInfo>,
        title: String? = "",
        identifyId: Int? = 0,
    ) {
        selectItemBottomSheetDialog =
            SelectItemBottomSheetDialog.newInstance(mContext, list, title, identifyId, this)
        selectItemBottomSheetDialog.show()
    }

    override fun onSelectItem(position: Int, action: Int, productType: Int) {
        if (action == AppConstants.DialogIdentifier.SELECT_LEAF_TYPE) {
            binding.edtLeafType.setText(teaGardenConfigurationResponse?.leafTypes!![position].name)
            mConfigurationItemInfo?.lu_leaf_type_id =
                teaGardenConfigurationResponse?.leafTypes!![position]._id
            selectItemBottomSheetDialog.dismiss()
        } else if (action == AppConstants.DialogIdentifier.SELECT_AREA) {
            binding.edtArea.setText(teaGardenConfigurationResponse?.gardenAreas!![position].name)
            mConfigurationItemInfo?.lu_garden_area_id =
                teaGardenConfigurationResponse?.gardenAreas!![position]._id
            selectItemBottomSheetDialog.dismiss()
        }
    }
}