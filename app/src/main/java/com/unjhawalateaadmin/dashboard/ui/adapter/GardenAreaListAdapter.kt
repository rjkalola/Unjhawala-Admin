package com.unjhawalateaadmin.dashboard.data.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.dashboard.data.model.ConfigurationItemInfo
import com.unjhawalateaadmin.databinding.RowGardenAreaListItemBinding
import com.unjhawalateaadmin.databinding.RowTeaConfigurationBinding
import com.unjhawalateaadmin.databinding.RowTeaGardenListItemBinding
import com.unjhawalateaadmin.databinding.RowTeaSampleListItemBinding


class GardenAreaListAdapter(
    var mContext: Context,
    var list: MutableList<ConfigurationItemInfo>,
    var listener: SelectItemListener?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listAll: MutableList<ConfigurationItemInfo> = ArrayList()

    init {
        this.listAll.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_garden_area_list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        val info = list[position]
        itemViewHolder.getData(info)
        itemViewHolder.binding.routMainView.setOnClickListener {
//            listener!!.onSelectItem(position, AppConstants.Action.RETAILER_DETAILS, 0)
        }

        if (info.status == 1) {
            itemViewHolder.binding.routMainView.setBackgroundResource(R.drawable.retailer_item_bg)
            itemViewHolder.binding.txtTitle.alpha = 1f
        } else {
            itemViewHolder.binding.routMainView.setBackgroundResource(R.drawable.retailer_item_transparent_bg)
            itemViewHolder.binding.txtTitle.alpha = 0.4f

        }

        itemViewHolder.binding.txtEdit.setOnClickListener {
            listener!!.onSelectItem(position, AppConstants.Action.EDIT_CONFIGURATION_ITEM, 0)
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
        var binding: RowGardenAreaListItemBinding = DataBindingUtil.bind(itemView)!!
        fun getData(info: ConfigurationItemInfo?) {
            binding.info = info
        }
    }

    fun addData(list: MutableList<ConfigurationItemInfo>) {
        listAll.addAll(list)
        this.list.clear()
        this.list.addAll(listAll)
        notifyDataSetChanged()
    }

    fun updateItem(selectedPosition: Int, info: ConfigurationItemInfo) {
        list[selectedPosition] = info
        notifyItemChanged(selectedPosition)
    }

}