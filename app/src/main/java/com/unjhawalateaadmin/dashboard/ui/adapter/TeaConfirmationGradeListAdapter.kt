package com.unjhawalateaadmin.dashboard.data.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.dashboard.data.model.AvailableTeaSampleInfo
import com.unjhawalateaadmin.databinding.RowTeaConfirmationGradeListItemBinding
import com.unjhawalateaadmin.databinding.RowTeaConfirmationListItemBinding


class TeaConfirmationGradeListAdapter(
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
                .inflate(R.layout.row_tea_confirmation_grade_list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        val info = list[position]
        itemViewHolder.getData(info)
        if (position % 2 == 0)
            itemViewHolder.binding.routMainView.setBackgroundResource(R.drawable.img_tea_confirm_grade_bg1)
        else
            itemViewHolder.binding.routMainView.setBackgroundResource(R.drawable.img_tea_confirm_grade_bg2)
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
        var binding: RowTeaConfirmationGradeListItemBinding = DataBindingUtil.bind(itemView)!!

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