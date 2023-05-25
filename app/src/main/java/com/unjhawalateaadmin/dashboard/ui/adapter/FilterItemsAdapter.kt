package com.unjhawalateaadmin.dashboard.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import com.unjhawalateaadmin.databinding.RowFilterItemsListBinding
import java.util.*

class FilterItemsAdapter(
    private val mContext: Context, list: MutableList<ModuleInfo>, listener: SelectItemListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: MutableList<ModuleInfo>
    private val listAll: MutableList<ModuleInfo>
    private val listener: SelectItemListener?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_filter_items_list, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data: ModuleInfo = list[position]
        val itemViewHolder = holder as ItemViewHolder
        if (data.is_selected) itemViewHolder.binding.imgCheck.setColorFilter(
            mContext.resources.getColor(
                R.color.colorAccent
            )
        ) else itemViewHolder.binding.imgCheck.setColorFilter(
            mContext.resources.getColor(R.color.colorUnCheck)
        )
        itemViewHolder.binding.routMainView.setOnClickListener { v ->
            data.is_selected = !data.is_selected
            listener?.onSelectItem(position, 0,0)
            notifyDataSetChanged()
        }
        itemViewHolder.getData(data)
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

    private inner class ItemViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {
        var binding: RowFilterItemsListBinding = DataBindingUtil.bind(itemView!!)!!
        fun getData(info: ModuleInfo?) {
            binding.info = info
        }

    }

    fun getList(): List<ModuleInfo> {
        return listAll
    }

    fun setList(list: MutableList<ModuleInfo>) {
        this.list = list
    }

    // Filter Class
    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        list.clear()
        if (charText.length == 0) {
            list.addAll(listAll)
        } else {
            for (wp in listAll) {
                try {
                    if (java.lang.String.valueOf(wp.name).toLowerCase(Locale.getDefault())
                            .contains(charText)
                    ) {
                        list.add(wp)
                    }
                } catch (e: Exception) {
                    Log.e(javaClass.name, "Exception in Filter :" + e.message)
                }
            }
        }
        notifyDataSetChanged()
    }

    init {
        listAll = ArrayList<ModuleInfo>()
        listAll.addAll(list)
        this.list = list
        this.listener = listener
    }
}