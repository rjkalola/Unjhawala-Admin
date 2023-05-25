package com.unjhawalateaadmin.dashboard.data.ui.adapter

import android.content.Context
import android.graphics.Color
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
import com.unjhawalateaadmin.databinding.RowTeaSampleListItemBinding
import com.unjhawalateaadmin.databinding.RowTeaSampleRatingListBinding


class TeaSampleRatingAdapter(
    var mContext: Context,
    var listener: SelectItemListener?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val colorList1: Array<String> = mContext.resources.getStringArray(R.array.ratingColorList1)
    val colorList2: Array<String> = mContext.resources.getStringArray(R.array.ratingColorList2)
    var selectedPosition = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_tea_sample_rating_list, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder

        itemViewHolder.binding.txtRating.text = (position + 1).toString()

        if (position == selectedPosition) {
            itemViewHolder.binding.imgColor1.setColorFilter(Color.parseColor(colorList1[position]))
            itemViewHolder.binding.imgColor2.setColorFilter(Color.parseColor(colorList1[position]))
            itemViewHolder.binding.txtRating.setTextColor(mContext.resources.getColor(R.color.colorWhite))
        } else {
            itemViewHolder.binding.imgColor1.setColorFilter(Color.parseColor(colorList2[position]))
            itemViewHolder.binding.imgColor2.setColorFilter(Color.parseColor(colorList1[position]))
            itemViewHolder.binding.txtRating.setTextColor(Color.parseColor(colorList1[position]))
        }

        itemViewHolder.binding.routMainView.setOnClickListener {
            selectedPosition = position
            listener?.onSelectItem(position, AppConstants.DialogIdentifier.TEA_SAMPLE_RATING, 0)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return colorList1.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    public fun setRating(rating: String) {
        if (!StringHelper.isEmpty(rating)) {
            selectedPosition = if (rating.toInt() == 0)
                0
            else
                rating.toInt() - 1
        }
    }

    private inner class ItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var binding: RowTeaSampleRatingListBinding = DataBindingUtil.bind(itemView)!!

    }

}