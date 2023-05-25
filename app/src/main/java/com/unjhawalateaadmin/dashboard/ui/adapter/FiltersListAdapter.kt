package com.unjhawalateaadmin.dashboard.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.imateplus.utilities.utils.StringHelper
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import com.unjhawalateaadmin.dashboard.callback.SelectFilterListener
import com.unjhawalateaadmin.databinding.RowFiltersListBinding

class FiltersListAdapter(private val mContext: Context, list: List<ModuleInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listFilters: List<ModuleInfo> = list
    var selectedFilter = 0
    private var listener: SelectFilterListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_filters_list, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data: ModuleInfo = listFilters[position]
        val itemViewHolder = holder as ItemViewHolder
        var upperString = ""

        if (!StringHelper.isEmpty(data.name)) upperString =
            data.name.substring(0, 1).toUpperCase() + data.name.substring(1)

        itemViewHolder.binding.txtFilterName.text = upperString

        if (position == selectedFilter)
            itemViewHolder.binding.routMainView.setBackgroundColor(mContext.resources.getColor(R.color.colorWindowBackground))
        else
            itemViewHolder.binding.routMainView.setBackgroundColor(mContext.resources.getColor(R.color.colorTitleBg))

        if (data.count == 0)
            itemViewHolder.binding.txtFilterCount.visibility = View.GONE
        else
            itemViewHolder.binding.txtFilterCount.visibility = View.VISIBLE

        itemViewHolder.binding.routMainView.setOnClickListener { v ->
            selectedFilter = position
            listener?.onSelectFilter(position)
            notifyDataSetChanged()
        }
        itemViewHolder.getData(data)
    }

    override fun getItemCount(): Int {
        return listFilters.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private inner class ItemViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {
        var binding: RowFiltersListBinding = DataBindingUtil.bind(itemView!!)!!
        fun getData(info: ModuleInfo?) {
            binding.info = info
        }

    }

    fun setListener(listener: SelectFilterListener?) {
        this.listener = listener
    }

}