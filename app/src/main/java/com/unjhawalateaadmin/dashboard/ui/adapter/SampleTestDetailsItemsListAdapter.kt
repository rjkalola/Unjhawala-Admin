package com.unjhawalateaadmin.dashboard.data.ui.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import com.unjhawalateaadmin.dashboard.data.model.TeaSourceLevelInfo
import com.unjhawalateaadmin.databinding.RowSampleTestDetailsItemListBinding


class SampleTestDetailsItemsListAdapter(
    var mContext: Context,
    var list: MutableList<ModuleInfo>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_sample_test_details_item_list, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        val info = list[position]
        itemViewHolder.getData(info)

        itemViewHolder.binding.txtTitle.text = info.label

        if (position % 2 == 0) {
            itemViewHolder.binding.routMainView.gravity = Gravity.CENTER_VERTICAL
        } else {
            itemViewHolder.binding.routMainView.gravity = Gravity.CENTER_VERTICAL or Gravity.END
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private inner class ItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var binding: RowSampleTestDetailsItemListBinding = DataBindingUtil.bind(itemView)!!
        fun getData(info: ModuleInfo?) {
            binding.info = info
        }
    }

}