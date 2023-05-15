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
import com.unjhawalateaadmin.common.utils.SwipeAndDragHelper
import com.unjhawalateaadmin.dashboard.callback.StoreConfigurationItemPositionListener
import com.unjhawalateaadmin.dashboard.data.model.ConfigurationItemInfo
import com.unjhawalateaadmin.dashboard.data.ui.adapter.GardenAreaManagePositionListAdapter
import com.unjhawalateaadmin.databinding.DialogGardenAreaManagePositionBinding


class GardenAreaManagePositionDialog(mContext: Context?) :
    BottomSheetDialog(mContext!!, R.style.CustomBottomSheetDialogTheme) {
    private lateinit var binding: DialogGardenAreaManagePositionBinding
    private var adapter: GardenAreaManagePositionListAdapter? = null

    companion
    object {
        private var mListener: StoreConfigurationItemPositionListener? = null
        private lateinit var mContext: Context
        private var itemType = 0
        var list: MutableList<ConfigurationItemInfo> = ArrayList()

        fun newInstance(
            context: Context?,
            mList:MutableList<ConfigurationItemInfo>,
            configType: Int,
            listener: StoreConfigurationItemPositionListener
        ): GardenAreaManagePositionDialog {
            this.mListener = listener
            this.mContext = context!!
            this.itemType = configType
            this.list = mList
            return GardenAreaManagePositionDialog(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sheetView: View =
            layoutInflater.inflate(R.layout.dialog_garden_area_manage_position, null)
        setContentView(sheetView)
        binding = DataBindingUtil.bind(sheetView)!!

        binding.imgClose.setOnClickListener {
            dismiss()
        }

        binding.txtSave.setOnClickListener {
            var itemIds = ""
            val listItemIds: MutableList<String> = ArrayList()
            for (info in list) {
                listItemIds.add(info.id)
            }
            itemIds = listItemIds.joinToString()
            mListener?.onStorePosition(itemType, itemIds)
            dismiss()
        }

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
        val linearLayoutManager =
            LinearLayoutManager(mContext)
        binding.recyclerView.layoutManager = linearLayoutManager
        adapter = GardenAreaManagePositionListAdapter(mContext, list);
        val swipeAndDragHelper = SwipeAndDragHelper(adapter!!)
        val touchHelper = ItemTouchHelper(swipeAndDragHelper)
        adapter!!.setTouchHelper(touchHelper)
        binding.recyclerView.adapter = adapter
        touchHelper.attachToRecyclerView(binding.recyclerView)
    }

}