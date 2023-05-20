package com.unjhawalateaadmin.dashboard.ui.dialog

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.imateplus.utilities.utils.StringHelper
import com.imateplus.utilities.utils.ValidationUtil
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.dashboard.callback.AddAvailableTeaSampleListener
import com.unjhawalateaadmin.dashboard.callback.AddConfigurationItemListener
import com.unjhawalateaadmin.dashboard.data.model.AvailableTeaSampleInfo
import com.unjhawalateaadmin.dashboard.data.model.ConfigurationItemInfo
import com.unjhawalateaadmin.databinding.DialogAddTeaSampleQuantityBinding

class AddTeaSampleQuantityDialog(mContext: Context?) :
    BottomSheetDialog(mContext!!, R.style.CustomBottomSheetDialogTheme), TextWatcher {
    private lateinit var binding: DialogAddTeaSampleQuantityBinding

    companion
    object {
        private var mListener: AddAvailableTeaSampleListener? = null
        private lateinit var mContext: Context
        private var mAvailableTeaSampleInfo: AvailableTeaSampleInfo? = null

        fun newInstance(
            context: Context,
            mAvailableTeaSampleInfo: AvailableTeaSampleInfo,
            mAddAvailableTeaSampleListener: AddAvailableTeaSampleListener
        ): AddTeaSampleQuantityDialog {
            this.mListener = mAddAvailableTeaSampleListener
            this.mContext = context
            this.mAvailableTeaSampleInfo = mAvailableTeaSampleInfo
            return AddTeaSampleQuantityDialog(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sheetView: View = layoutInflater.inflate(R.layout.dialog_add_tea_sample_quantity, null)
        setContentView(sheetView)
        binding = DataBindingUtil.bind(sheetView)!!
        binding.info = mAvailableTeaSampleInfo

        if (mAvailableTeaSampleInfo != null && !StringHelper.isEmpty(mAvailableTeaSampleInfo?.display_name))
            binding.txtTitle.text = mAvailableTeaSampleInfo?.display_name

        binding.txtAdd.setOnClickListener {
            if (valid()) {
                mAvailableTeaSampleInfo!!.bag = binding.edtBag.text.toString().trim()
                mAvailableTeaSampleInfo!!.weight = binding.edtWeight.text.toString().trim()
                mAvailableTeaSampleInfo!!.rate = binding.edtRate.text.toString().trim()
                mAvailableTeaSampleInfo!!.total_quantity =
                    binding.edtTotalQuantity.text.toString().trim()
                mListener?.onAddAvailableTeaSample(mAvailableTeaSampleInfo!!)
                dismiss()
            }
        }

        binding.imgClose.setOnClickListener { dismiss() }

        binding.edtBag.addTextChangedListener(this)
        binding.edtWeight.addTextChangedListener(this)

    }

    private fun valid(): Boolean {
        var valid = true

//        if (!ValidationUtil.isEmptyEditText(binding.edtTotalQuantity.text.toString().trim())) {
//            binding.layoutTotalQuantity.isErrorEnabled = false
//            binding.edtTotalQuantity.error = null
//        } else {
//            ValidationUtil.setErrorIntoInputTextLayout(
//                binding.edtTotalQuantity, binding.layoutTotalQuantity,
//                mContext.getString(R.string.empty_edittext_error)
//            )
//            valid = false
//        }

        if (!ValidationUtil.isEmptyEditText(binding.edtRate.text.toString().trim())) {
            binding.layoutRate.isErrorEnabled = false
            binding.edtRate.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtRate, binding.layoutRate,
                mContext.getString(R.string.empty_edittext_error)
            )
            valid = false
        }
        if (!ValidationUtil.isEmptyEditText(binding.edtWeight.text.toString().trim())) {
            binding.layoutWeight.isErrorEnabled = false
            binding.edtWeight.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtWeight, binding.layoutWeight,
                mContext.getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(binding.edtBag.text.toString().trim())) {
            binding.layoutBag.isErrorEnabled = false
            binding.edtBag.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtBag, binding.layoutBag,
                mContext.getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        return valid
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        var totalAmount = 0
        if (!StringHelper.isEmpty(binding.edtBag.text.toString().trim())
            && !StringHelper.isEmpty(binding.edtWeight.text.toString().trim())
        ) {
            val bag = binding.edtBag.text.toString().trim().toInt()
            val weight = binding.edtWeight.text.toString().trim().toInt()
            totalAmount = bag * weight
            binding.edtTotalQuantity.setText(totalAmount.toString())
        } else {
            binding.edtTotalQuantity.setText("")
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun onStart() {
        super.onStart()
//        this.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
//        this.window!!.setBackgroundDrawableResource(android.R.color.transparent);
        this.window!!.setLayout(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        this.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
}