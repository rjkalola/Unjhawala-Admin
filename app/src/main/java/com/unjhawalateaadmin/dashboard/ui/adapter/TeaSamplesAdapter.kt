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
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleInfo
import com.unjhawalateaadmin.databinding.RowTeaConfigurationBinding
import com.unjhawalateaadmin.databinding.RowTeaSampleListItemBinding


class TeaSamplesAdapter(
    var mContext: Context,
    var list: MutableList<TeaSampleInfo>,
    var listener: SelectItemListener?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listAll: MutableList<TeaSampleInfo> = ArrayList()

    init {
        this.listAll.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_tea_sample_list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        val info = list[position]
        itemViewHolder.getData(info)
        itemViewHolder.binding.imgEdit.setOnClickListener {
            listener!!.onSelectItem(position, AppConstants.Action.EDIT_TEA_SAMPLE, 0)
        }
        itemViewHolder.binding.imgFilter.setOnClickListener {
            listener!!.onSelectItem(position, AppConstants.Action.ADD_TEA_SAMPLE_TESTING, 0)
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
        var binding: RowTeaSampleListItemBinding = DataBindingUtil.bind(itemView)!!
        fun getData(info: TeaSampleInfo?) {
            binding.info = info
        }

    }

    fun addData(list: MutableList<TeaSampleInfo>) {
        listAll.addAll(list)
        this.list.clear()
        this.list.addAll(listAll)
        notifyDataSetChanged()
    }

}