package com.unjhawalateaadmin.dashboard.data.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.dashboard.ui.activity.GardenAreaActivity
import com.unjhawalateaadmin.dashboard.ui.activity.TeaGardenActivity
import com.unjhawalateaadmin.databinding.RowTeaConfigurationBinding


class TeaConfigurationAdapter(
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
                .inflate(R.layout.row_tea_configuration, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder

        if (position == 2) {
            itemViewHolder.binding.txtTitle.text = "Tea Garden"
        }

        itemViewHolder.binding.routMainView.setOnClickListener {
//            listener!!.onSelectItem(position, AppConstants.Action.RETAILER_DETAILS, 0)
//            if (position == 0) {
            (mContext as BaseActivity).moveActivity(
                mContext,
                GardenAreaActivity::class.java,
                false,
                false,
                null
            )
//            } else if (position == 2) {
//                (mContext as BaseActivity).moveActivity(
//                    mContext,
//                    TeaGardenActivity::class.java,
//                    false,
//                    false,
//                    null
//                )
//            }
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
        var binding: RowTeaConfigurationBinding = DataBindingUtil.bind(itemView)!!
//        fun getData(info: RetailerInfo?) {
//            binding.info = info
//        }

    }

}