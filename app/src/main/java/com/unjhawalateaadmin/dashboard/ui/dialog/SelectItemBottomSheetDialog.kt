package com.unjhawalateaadmin.dashboard.ui.dialog

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.imateplus.utilities.utils.CollectionUtils
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import com.unjhawalateaadmin.databinding.BottomSheetDialogSelectItemBinding
import com.unjhawalateaadmin.databinding.RowBottomDialogSelectItemBinding
import java.util.*
    
class SelectItemBottomSheetDialog(mContext: Context?) :
    BottomSheetDialog(mContext!!) {
    private lateinit var binding: BottomSheetDialogSelectItemBinding
    private lateinit var adapter: ListAdapter

    companion object {
        private var mListener: SelectItemListener? = null
        private var selectedItem: Int? = 0
        private var list: MutableList<ModuleInfo> = ArrayList()
        private var identifyId: Int? = 0
        private var title: String? = null
        private lateinit var mContext: Context

        fun newInstance(
            context: Context?,
            list: MutableList<ModuleInfo>,
            title: String? = "",
            identifyId: Int? = 0,
            mListener: SelectItemListener?
        ): SelectItemBottomSheetDialog {
            this.selectedItem = selectedItem!!
            this.identifyId = identifyId!!
            this.mListener = mListener
            this.mContext = context!!
            this.list.clear()
            for (i in 0 until list.size) {
                list[i].position = i
                this.list.add(list[i])
            }
            Log.e("test", "list sizeee:" + list.size)
            this.title = title
            return SelectItemBottomSheetDialog(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sheetView: View = layoutInflater.inflate(R.layout.bottom_sheet_dialog_select_item, null)
        setContentView(sheetView)
        binding = DataBindingUtil.bind(sheetView)!!
        binding.txtTitle.text = title

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                adapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding.imgClose.setOnClickListener {
            dismiss()
        }

        setAdapter()
    }

    private fun setAdapter() {
        Log.e("test", "list.size:" + list.size);
        if (CollectionUtils.isNotEmpty(list)) {
            binding.rvItems.visibility = View.VISIBLE
            adapter = ListAdapter(
                list,
                mListener!!,
                identifyId!!
            )
            binding.rvItems.layoutManager = LinearLayoutManager(mContext)
            binding.rvItems.adapter = adapter
        } else {
            binding.rvItems.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        this.window!!.setLayout(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }

    class ListAdapter(
        private var mList: MutableList<ModuleInfo>,
        var listener: SelectItemListener,
        var identifyId: Int,
    ) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var listOfAllData: MutableList<ModuleInfo> = ArrayList()

        init {
            listOfAllData.addAll(mList)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_bottom_dialog_select_item, parent, false)
            return ItemViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val data: ModuleInfo = mList[position]
            val itemViewHolder = holder as ItemViewHolder

            itemViewHolder.binding.routMainView.setOnClickListener { v ->
                listener.onSelectItem(
                    data.position,
                    identifyId, 0
                )
            }
            itemViewHolder.getData(data)
        }

        override fun getItemCount(): Int {
            return mList.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        private inner class ItemViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            var binding: RowBottomDialogSelectItemBinding = DataBindingUtil.bind(itemView)!!
            fun getData(info: ModuleInfo) {
                binding.info = info
            }
        }

        // Filter Class
        fun filter(charText: String) {
            var charText = charText
            charText = charText.toLowerCase(Locale.getDefault())
            mList.clear()
            if (charText.isEmpty()) {
                mList.addAll(listOfAllData)
            } else {
                for (wp in listOfAllData) {
                    try {
                        if (java.lang.String.valueOf(wp.name).toLowerCase(Locale.getDefault())
                                .contains(charText)
                        ) {
                            mList.add(wp)
                        }
                    } catch (e: Exception) {
                        Log.e(javaClass.name, "Exception in Filter :" + e.message)
                    }
                }
            }
            notifyDataSetChanged()
        }
    }


}