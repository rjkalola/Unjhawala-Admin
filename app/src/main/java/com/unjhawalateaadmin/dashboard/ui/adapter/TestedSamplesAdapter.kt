package com.unjhawalateaadmin.dashboard.data.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.imateplus.utilities.utils.StringHelper
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleInfo
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleTestingInfo
import com.unjhawalateaadmin.databinding.RowTeaSampleListItemBinding
import com.unjhawalateaadmin.databinding.RowTestedSampleListItemBinding


class TestedSamplesAdapter(
    var mContext: Context,
    var list: MutableList<TeaSampleTestingInfo>,
    var listener: SelectItemListener?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listAll: MutableList<TeaSampleTestingInfo> = ArrayList()

    init {
        this.listAll.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_tested_sample_list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        val info = list[position]
        itemViewHolder.getData(info)

        if (!StringHelper.isEmpty(info.rate) && info.rate!!.toDouble() > 0) {
            itemViewHolder.binding.imgRate.visibility = View.VISIBLE
            itemViewHolder.binding.txtRate.visibility = View.VISIBLE
            itemViewHolder.binding.dividerRate.visibility = View.VISIBLE
        } else {
            itemViewHolder.binding.imgRate.visibility = View.GONE
            itemViewHolder.binding.txtRate.visibility = View.GONE
            itemViewHolder.binding.dividerRate.visibility = View.GONE
        }

        itemViewHolder.binding.imgView.setOnClickListener {
            listener!!.onSelectItem(position, AppConstants.Action.VIEW_TEA_SAMPLE, 0)
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
        var binding: RowTestedSampleListItemBinding = DataBindingUtil.bind(itemView)!!
        fun getData(info: TeaSampleTestingInfo?) {
            binding.info = info
        }

    }

    fun addData(list: MutableList<TeaSampleTestingInfo>) {
        listAll.addAll(list)
        this.list.clear()
        this.list.addAll(listAll)
        notifyDataSetChanged()
    }

}