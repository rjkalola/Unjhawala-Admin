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
import com.unjhawalateaadmin.dashboard.data.model.AvailableTeaSampleInfo
import com.unjhawalateaadmin.databinding.RowTeaConfirmationListItemBinding


class TeaConfirmationListAdapter(
    var mContext: Context,
    var list: MutableList<AvailableTeaSampleInfo>,
    var listener: SelectItemListener?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listAll: MutableList<AvailableTeaSampleInfo> = ArrayList()

    init {
        this.listAll.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_tea_confirmation_list_item, parent, false)
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
        var binding: RowTeaConfirmationListItemBinding = DataBindingUtil.bind(itemView)!!
        fun getData(info: AvailableTeaSampleInfo?) {
            binding.info = info
        }
    }

    fun addData(list: MutableList<AvailableTeaSampleInfo>) {
        listAll.addAll(list)
        this.list.clear()
        this.list.addAll(listAll)
        notifyDataSetChanged()
    }

}