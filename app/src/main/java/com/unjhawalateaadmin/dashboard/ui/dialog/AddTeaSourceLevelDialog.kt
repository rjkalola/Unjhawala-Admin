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
import com.unjhawalateaadmin.dashboard.callback.AddTeaSourceItemListener
import com.unjhawalateaadmin.dashboard.data.model.TeaSourceLevelInfo
import com.unjhawalateaadmin.dashboard.data.model.TeaSourceLevelListResponse
import com.unjhawalateaadmin.databinding.DialogAddTeaSourceLevelBinding

class AddTeaSourceLevelDialog(mContext: Context?) :
    BottomSheetDialog(mContext!!, R.style.CustomBottomSheetDialogTheme), SelectItemListener {
    private lateinit var binding: DialogAddTeaSourceLevelBinding

    companion
    object {
        private var mListener: AddTeaSourceItemListener? = null
        private lateinit var mContext: Context
        private var level: Int = 0
        private var mTeaSourceLevelInfo: TeaSourceLevelInfo? = null
        private var teaSourceLevelListResponse: TeaSourceLevelListResponse? = null
        private lateinit var selectItemBottomSheetDialog: SelectItemBottomSheetDialog
        var firstLevels: MutableList<ModuleInfo> = ArrayList()
        var secondLevels: MutableList<ModuleInfo> = ArrayList()

        fun newInstance(
            context: Context?,
            info: TeaSourceLevelInfo?,
            teaSourceLevelListResponse: TeaSourceLevelListResponse?,
            level: Int,
            listener: AddTeaSourceItemListener
        ): AddTeaSourceLevelDialog {
            this.mListener = listener
            this.mContext = context!!
            this.level = level
            this.teaSourceLevelListResponse = teaSourceLevelListResponse
            if (info != null)
                this.mTeaSourceLevelInfo = info
            else
                this.mTeaSourceLevelInfo = TeaSourceLevelInfo()
            return AddTeaSourceLevelDialog(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sheetView: View = layoutInflater.inflate(R.layout.dialog_add_tea_source_level, null)
        setContentView(sheetView)
        binding = DataBindingUtil.bind(sheetView)!!
        binding.info = mTeaSourceLevelInfo

        if (!StringHelper.isEmpty(mTeaSourceLevelInfo?.id))
            binding.txtTitle.text = "Edit " + mContext.getString(R.string.tea_source)
        else
            binding.txtTitle.text = "Add " + mContext.getString(R.string.tea_source)

        when (level) {
            AppConstants.Type.TEA_SOURCE_LEVEL_1 -> {

            }
            AppConstants.Type.TEA_SOURCE_LEVEL_2 -> {
                binding.layoutLevel1.visibility = View.VISIBLE
            }
            AppConstants.Type.TEA_SOURCE_LEVEL_3 -> {
                binding.layoutLevel1.visibility = View.VISIBLE
                binding.layoutLevel2.visibility = View.VISIBLE
            }
        }

        firstLevels.clear()
        for (data in teaSourceLevelListResponse?.Data!!) {
            val info = ModuleInfo()
            info._id = data._id!!
            info.name = data.name!!
            firstLevels.add(info)
        }

        binding.txtSave.setOnClickListener {
            if (valid()) {
                mTeaSourceLevelInfo!!.name = binding.edtName.text.toString().trim()
                mListener?.onAddTeaSource(level, mTeaSourceLevelInfo!!)
                dismiss()
            }
        }

        binding.edtLevel1.setOnClickListener {
            showSelectItemDialog(
                firstLevels,
                mContext.getString(R.string.select_first_level),
                AppConstants.DialogIdentifier.SELECT_FIRST_LEVEL
            )
        }

        binding.edtLevel2.setOnClickListener {
            showSelectItemDialog(
                secondLevels,
                mContext.getString(R.string.select_second_level),
                AppConstants.DialogIdentifier.SELECT_SECOND_LEVEL
            )
        }

        binding.imgClose.setOnClickListener {
            dismiss()
        }

    }

    private fun valid(): Boolean {
        var valid = true

        if (!ValidationUtil.isEmptyEditText(binding.edtName.text.toString().trim())) {
            binding.layoutName.isErrorEnabled = false
            binding.edtName.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtName, binding.layoutName,
                mContext.getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        when (level) {
            AppConstants.Type.TEA_SOURCE_LEVEL_2 -> {
                if (!ValidationUtil.isEmptyEditText(binding.edtLevel1.text.toString().trim())) {
                    binding.layoutLevel1.isErrorEnabled = false
                    binding.edtLevel1.error = null
                } else {
                    ValidationUtil.setErrorIntoInputTextLayout(
                        binding.edtLevel1, binding.layoutLevel1,
                        mContext.getString(R.string.empty_edittext_error)
                    )
                    valid = false
                }
            }
            AppConstants.Type.TEA_SOURCE_LEVEL_3 -> {
                if (!ValidationUtil.isEmptyEditText(binding.edtLevel1.text.toString().trim())) {
                    binding.layoutLevel1.isErrorEnabled = false
                    binding.edtLevel1.error = null
                } else {
                    ValidationUtil.setErrorIntoInputTextLayout(
                        binding.edtLevel1, binding.layoutLevel1,
                        mContext.getString(R.string.empty_edittext_error)
                    )
                    valid = false
                }

                if (!ValidationUtil.isEmptyEditText(binding.edtLevel2.text.toString().trim())) {
                    binding.layoutLevel2.isErrorEnabled = false
                    binding.edtLevel2.error = null
                } else {
                    ValidationUtil.setErrorIntoInputTextLayout(
                        binding.edtLevel2, binding.layoutLevel2,
                        mContext.getString(R.string.empty_edittext_error)
                    )
                    valid = false
                }
            }
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
        if (action == AppConstants.DialogIdentifier.SELECT_FIRST_LEVEL) {
            binding.edtLevel1.setText(firstLevels[position].name)
            mTeaSourceLevelInfo?.level_1 =
                firstLevels[position]._id
            selectItemBottomSheetDialog.dismiss()

            secondLevels.clear()
            binding.edtLevel2.setText("")
            mTeaSourceLevelInfo?.level_2 = ""

            if (teaSourceLevelListResponse?.Data!![position].first_levels.isNotEmpty()) {
                for (data in teaSourceLevelListResponse?.Data!![position].first_levels) {
                    val info = ModuleInfo()
                    info._id = data._id!!
                    info.name = data.name!!
                    secondLevels.add(info)
                }
            }
        } else if (action == AppConstants.DialogIdentifier.SELECT_SECOND_LEVEL) {
            binding.edtLevel2.setText(secondLevels[position].name)
            mTeaSourceLevelInfo?.level_2 =
                secondLevels[position]._id
            selectItemBottomSheetDialog.dismiss()
        }
    }
}