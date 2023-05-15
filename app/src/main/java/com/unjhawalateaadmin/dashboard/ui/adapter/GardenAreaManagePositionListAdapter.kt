package com.unjhawalateaadmin.dashboard.data.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.data.model.SwipeItemInfo
import com.unjhawalateaadmin.common.utils.SwipeAndDragHelper
import com.unjhawalateaadmin.dashboard.data.model.ConfigurationItemInfo
import com.unjhawalateaadmin.databinding.RowGardenAreaManagingPositionListItemBinding
import java.util.*
import kotlin.collections.ArrayList


class GardenAreaManagePositionListAdapter(
    var mContext: Context,
    var list: MutableList<ConfigurationItemInfo>,
//    var listener: SelectItemListener?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), SwipeAndDragHelper.ActionCompletionContract {
//    private var listAll: MutableList<SwipeItemInfo> = ArrayList()
    private var touchHelper: ItemTouchHelper? = null

//    init {
//        this.listAll.addAll(list)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_garden_area_managing_position_list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        val info = list[position]
        itemViewHolder.binding.txtPosition.text = info.position.toString()
        itemViewHolder.binding.txtTitle.text = info.name

        itemViewHolder.binding.imgDrag.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                touchHelper!!.startDrag(holder)
            }
            false
        })

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
        var binding: RowGardenAreaManagingPositionListItemBinding = DataBindingUtil.bind(itemView)!!
//        fun getData(info: RetailerInfo?) {
//            binding.info = info
//        }

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    fun setTouchHelper(touchHelper: ItemTouchHelper) {
        this.touchHelper = touchHelper
    }

    override fun onViewMove(oldPosition: Int, newPosition: Int) {
     /*   val targetItem: SwipeItemInfo = list[oldPosition]

        val item = SwipeItemInfo()
        item.copyData(targetItem)

        list.removeAt(oldPosition)
        list.add(newPosition, item)

        notifyItemMoved(oldPosition, )*/

        Collections.swap(list, oldPosition, newPosition);
        notifyItemMoved(oldPosition, newPosition);

        Log.e("test", "notifyItemMoved: $oldPosition to $newPosition")
    }

    override fun onViewMoved(newPosition: Int) {

    }

    override fun onViewSwiped(position: Int) {

    }

    override fun onDragCompleted() {
//        notifyDataSetChanged()
    }

}