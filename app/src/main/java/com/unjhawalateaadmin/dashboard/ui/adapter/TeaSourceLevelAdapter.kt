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
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleInfo
import com.unjhawalateaadmin.dashboard.data.model.TeaSourceLevelInfo
import com.unjhawalateaadmin.databinding.RowTeaSampleListItemBinding
import com.unjhawalateaadmin.databinding.RowTeaSourceLevelListItemBinding


class TeaSourceLevelAdapter(
    var mContext: Context,
    var list: MutableList<TeaSourceLevelInfo>,
    var level: Int,
    var listener: SelectItemListener?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_tea_source_level_list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        val info = list[position]

        when (level) {
            AppConstants.Type.TEA_SOURCE_LEVEL_1 -> {
                itemViewHolder.binding.textView1.text = info.name
            }
            AppConstants.Type.TEA_SOURCE_LEVEL_2 -> {
                itemViewHolder.binding.textView2.visibility = View.VISIBLE
                itemViewHolder.binding.textView1.text = info.level_1_name
                itemViewHolder.binding.textView2.text = info.name
            }
            AppConstants.Type.TEA_SOURCE_LEVEL_3 -> {
                itemViewHolder.binding.textView2.visibility = View.VISIBLE
                itemViewHolder.binding.textView1.text =
                    info.level_1_name + " > " + info.level_2_name
                itemViewHolder.binding.textView2.text = info.name
            }
        }

        itemViewHolder.binding.imgEdit.setOnClickListener {
            listener!!.onSelectItem(position, AppConstants.Action.EDIT_TEA_SOURCE, 0)
        }

        itemViewHolder.binding.imgDelete.setOnClickListener {
            listener!!.onSelectItem(position, AppConstants.Action.DELETE_TEA_SOURCE, 0)
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
        var binding: RowTeaSourceLevelListItemBinding = DataBindingUtil.bind(itemView)!!

    }

}