package com.unjhawalateaadmin.dashboard.data.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.databinding.RowGardenAreaListItemBinding
import com.unjhawalateaadmin.databinding.RowTeaConfigurationBinding
import com.unjhawalateaadmin.databinding.RowTeaGardenListItemBinding
import com.unjhawalateaadmin.databinding.RowTeaSampleListItemBinding


class GardenAreaListAdapter(
    var mContext: Context,
//    var list: MutableList<RetailerInfo>,
    var listener: SelectItemListener?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    private var listAll: MutableList<RetailerInfo> = ArrayList()

    init {
//        this.listAll.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_garden_area_list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder

        itemViewHolder.binding.routMainView.setOnClickListener {
//            listener!!.onSelectItem(position, AppConstants.Action.RETAILER_DETAILS, 0)
        }

    }

    override fun getItemCount(): Int {
        return 10
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
//        fun getData(info: RetailerInfo?) {
//            binding.info = info
//        }

    }

}