package com.unjhawalateaadmin.dashboard.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import com.unjhawalateaadmin.common.data.model.SwipeItemInfo
import com.unjhawalateaadmin.common.utils.SwipeAndDragHelper
import com.unjhawalateaadmin.dashboard.data.ui.adapter.GardenAreaManagePositionListAdapter
import com.unjhawalateaadmin.databinding.DialogGardenAreaManagePositionBinding

class GardenAreaManagePositionDialog(mContext: Context?) :
    BottomSheetDialog(mContext!!) {
    private lateinit var binding: DialogGardenAreaManagePositionBinding
    private var adapter: GardenAreaManagePositionListAdapter? = null

    companion
    object {
        //        private var mListener: AddCartQuantityListener? = null
        private lateinit var mContext: Context
        private var mQuantity = 0

        fun newInstance(
            context: Context?,
        ): GardenAreaManagePositionDialog {
//            this.mListener = mListener
            this.mContext = context!!
//            mProductInfo = productInfo
//            mProductComponentInfo = productComponentInfo
            mQuantity = 1

            return GardenAreaManagePositionDialog(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sheetView: View =
            layoutInflater.inflate(R.layout.dialog_garden_area_manage_position, null)
        setContentView(sheetView)
        binding = DataBindingUtil.bind(sheetView)!!
        binding.imgClose.setOnClickListener { dismiss() }
        setAdapter()
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

    private fun setAdapter() {
        val list: MutableList<SwipeItemInfo> = ArrayList()

        val aa = SwipeItemInfo()
        aa.name = "One"
        list.add(aa)

        val bb = SwipeItemInfo()
        bb.name = "Two"
        list.add(bb)

        val cc = SwipeItemInfo()
        cc.name = "Threess"
        list.add(cc)

        val dd = SwipeItemInfo()
        dd.name = "Four"
        list.add(dd)

        adapter = GardenAreaManagePositionListAdapter(mContext,list);
        val swipeAndDragHelper = SwipeAndDragHelper(adapter!!)
        val touchHelper = ItemTouchHelper(swipeAndDragHelper)
        adapter!!.setTouchHelper(touchHelper)
        binding.recyclerView.adapter = adapter
        touchHelper.attachToRecyclerView(binding.recyclerView)
        val linearLayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
    }

}