package com.unjhawalateaadmin.dashboard.data.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.imateplus.utilities.utils.StringHelper
import com.imateplus.utilities.utils.ValidationUtil
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.dashboard.data.model.TeaSeasonQualityInfo
import com.unjhawalateaadmin.dashboard.ui.dialog.AddTeaSeasonDialog
import com.unjhawalateaadmin.databinding.RowTeaSeasonQualityListItemBinding


class TeaSeasonQualityListAdapter(
    var mContext: Context,
    var list: MutableList<TeaSeasonQualityInfo>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listAll: MutableList<TeaSeasonQualityInfo> = ArrayList()
    private var checkValidation = false
    public var validate = true

    init {
        this.listAll.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_tea_season_quality_list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        val info = list[position]
        itemViewHolder.getData(info)

//        if (info.check || (!StringHelper.isEmpty(info.number_of_days) && info.number_of_days.toInt() > 0)) {
        if (info.check) {
            itemViewHolder.binding.cbQuality.isChecked = true
            itemViewHolder.binding.layoutDays.visibility = View.VISIBLE
        } else {
            itemViewHolder.binding.layoutDays.visibility = View.GONE
        }

        if (checkValidation) {
            if (info.check) {
                if (!StringHelper.isEmpty(itemViewHolder.binding.edtDays.text.toString().trim())
                    && itemViewHolder.binding.edtDays.text.toString().trim().toInt() > 0
                ) {
                    itemViewHolder.binding.layoutDays.error = null
                    itemViewHolder.binding.layoutDays.isErrorEnabled = false
                } else {
                    ValidationUtil.setErrorIntoInputTextLayout(
                        itemViewHolder.binding.edtDays, itemViewHolder.binding.layoutDays,
                        mContext.getString(R.string.empty_edittext_error)
                    )
                    validate = false
                }
            }
        } else {
            itemViewHolder.binding.layoutDays.error = null
            itemViewHolder.binding.layoutDays.isErrorEnabled = false
        }

        itemViewHolder.binding.cbQuality.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                info.check = isChecked
                if (!isChecked) {
                    itemViewHolder.binding.layoutDays.visibility = View.GONE
                } else {
                    itemViewHolder.binding.layoutDays.visibility = View.VISIBLE
                }
            }
        }

        itemViewHolder.binding.routMainView.setOnClickListener {
//            listener!!.onSelectItem(position, AppConstants.Action.RETAILER_DETAILS, 0)
        }

        if (position == list.size - 1) {
            checkValidation = false
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
        var binding: RowTeaSeasonQualityListItemBinding = DataBindingUtil.bind(itemView)!!
        fun getData(info: TeaSeasonQualityInfo?) {
            binding.info = info
        }
    }

    public fun checkValidation() {
        validate = true
        checkValidation = true
        notifyDataSetChanged()
    }


}