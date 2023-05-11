package com.unjhawalateaadmin.dashboard.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.databinding.DialogAddGardenAreaBinding

class AddGardenAreaDialog(mContext: Context?) :
    BottomSheetDialog(mContext!!) {
    private lateinit var binding: DialogAddGardenAreaBinding

    companion
    object {
//        private var mListener: AddCartQuantityListener? = null
        private lateinit var mContext: Context
        private var mQuantity = 0

        fun newInstance(
            context: Context?,
        ): AddGardenAreaDialog {
//            this.mListener = mListener
            this.mContext = context!!
//            mProductInfo = productInfo
//            mProductComponentInfo = productComponentInfo
            mQuantity = 1

            return AddGardenAreaDialog(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sheetView: View = layoutInflater.inflate(R.layout.dialog_add_garden_area, null)
        setContentView(sheetView)
        binding = DataBindingUtil.bind(sheetView)!!
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

}