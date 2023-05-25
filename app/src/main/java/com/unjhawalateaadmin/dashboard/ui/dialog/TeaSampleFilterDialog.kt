package com.unjhawalateaadmin.dashboard.ui.dialog

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.imateplus.utilities.callback.DialogButtonClickListener
import com.imateplus.utilities.utils.AlertDialogHelper
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.dashboard.callback.OnApplyFilterListener
import com.unjhawalateaadmin.dashboard.callback.SelectFilterListener
import com.unjhawalateaadmin.dashboard.ui.adapter.FilterItemsAdapter
import com.unjhawalateaadmin.dashboard.ui.adapter.FiltersListAdapter
import com.unjhawalateaadmin.databinding.DialogFiltersBinding


class TeaSampleFilterDialog(mContext: Context?) :
    BottomSheetDialog(mContext!!, R.style.CustomBottomSheetDialogTheme), View.OnClickListener,
    SelectFilterListener, SelectItemListener, DialogButtonClickListener {
    private lateinit var binding: DialogFiltersBinding
    private lateinit var adapterFilters: FiltersListAdapter
    private lateinit var adapterFilterItems: FilterItemsAdapter
    private var lastFilterIndex = 0

    companion
    object {
        private var mListener: OnApplyFilterListener? = null
        private lateinit var mContext: Context
        lateinit var filters: MutableList<ModuleInfo>

        fun newInstance(
            context: Context?,
            filters: MutableList<ModuleInfo>,
            listener: OnApplyFilterListener
        ): TeaSampleFilterDialog {
            this.mListener = listener
            this.mContext = context!!
            this.filters = filters
            return TeaSampleFilterDialog(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sheetView: View = layoutInflater.inflate(R.layout.dialog_filters, null)
        setContentView(sheetView)
        binding = DataBindingUtil.bind(sheetView)!!

        binding.imgClose.setOnClickListener(this)
        binding.txtApply.setOnClickListener(this)
        binding.txtReset.setOnClickListener(this)

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                adapterFilterItems.filter(s.toString())
            }
            override fun afterTextChanged(s: Editable) {}
        })

        binding.routMainView.visibility = View.VISIBLE
        binding.dividerBottom.visibility = View.VISIBLE
        binding.routButtonsView.visibility = View.VISIBLE

        setFiltersAdapter(filters)
        if (filters.size > 0)
            setFilterItemsAdapter(filters[0].data)

        behavior.state = BottomSheetBehavior.STATE_EXPANDED;

        binding.rvFilterItems.setOnTouchListener(View.OnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            v.onTouchEvent(event)
            true
        })

        this.behavior.isDraggable = false
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imgClose -> dismiss()
            R.id.txtReset -> {
                AlertDialogHelper.showDialog(
                    mContext,
                    "",
                    mContext.getString(R.string.msg_clear_filter),
                    mContext.getString(R.string.yes),
                    mContext.getString(R.string.no),
                    false,
                    this,
                    AppConstants.DialogIdentifier.CLEAR_FILTER
                )
            }
            R.id.txtApply -> {
                binding.edtSearch.setText("")
                adapterFilterItems.filter("")
//                val intent = Intent()
//                val bundle = Bundle()
//                bundle.putParcelable(
//                    AppConstants.IntentKey.FILTER_DATA, Parcels.wrap<FiltersResponse?>(
//                        filtersData
//                    )
//                )
//                intent.putExtras(bundle)
//                setResult(Activity.RESULT_OK, intent)
//                finish()
                mListener?.onApplyFilter(filters)
                dismiss()
            }
        }
    }

    public fun valid(): Boolean {
        var valid = false

        for (i in 0 until filters.size) {
            for (j in 0 until filters[i].data.size) {
                if (filters[i].data[j].is_selected)
                    valid = true
            }
        }

        return valid;
    }

    private fun setFiltersAdapter(list: MutableList<ModuleInfo>?) {
        if (list != null && list.isNotEmpty()) {
            binding.rvFilters.visibility = View.VISIBLE
            val linearLayoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            binding.rvFilters.layoutManager = linearLayoutManager
            binding.rvFilters.setHasFixedSize(true)
            adapterFilters = FiltersListAdapter(mContext, list)
            adapterFilters.setListener(this)
            binding.rvFilters.adapter = adapterFilters
        }
    }

    private fun setFilterItemsAdapter(list: MutableList<ModuleInfo>) {
        binding.rvFilterItems.visibility = View.VISIBLE
        val linearLayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        binding.rvFilterItems.layoutManager = linearLayoutManager
        binding.rvFilterItems.setHasFixedSize(true)
        adapterFilterItems = FilterItemsAdapter(mContext, list, this)
        binding.rvFilterItems.adapter = adapterFilterItems
    }

    private fun clearAllFilters() {
        lastFilterIndex = 0
        for (i in 0 until filters.size) {
            filters[i].count = 0
            for (j in 0 until filters[i].data.size) {
                filters[i].data[j].is_selected = false
            }
        }
        setFiltersAdapter(filters)
        if (filters.size > 0
        ) setFilterItemsAdapter(filters[0].data)
    }

    override fun onSelectFilter(position: Int) {
        var count = 0
        for (i in 0 until adapterFilterItems.getList().size) {
            if (adapterFilterItems.getList()[i].is_selected) count += 1
        }
        filters[lastFilterIndex].count = count
        lastFilterIndex = position
        binding.edtSearch.setText("")
        adapterFilterItems.filter("")
        setFilterItemsAdapter(filters[position].data)
    }

    override fun onSelectItem(position: Int, action: Int, productType: Int) {
        var count = 0
        for (i in 0 until adapterFilterItems.getList().size) {
            if (adapterFilterItems.getList()[i].is_selected) count = count + 1
        }
        filters.get(lastFilterIndex).count = count
        adapterFilters.notifyItemChanged(lastFilterIndex)
    }

    override fun onPositiveButtonClicked(dialogIdentifier: Int) {
        if (dialogIdentifier == AppConstants.DialogIdentifier.CLEAR_FILTER) {
            clearAllFilters()
        }
    }

    override fun onNegativeButtonClicked(dialogIdentifier: Int) {

    }

    override fun onStart() {
        super.onStart()
//        this.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
//        this.window!!.setBackgroundDrawableResource(android.R.color.transparent);
        this.window!!.setLayout(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }

    fun BottomSheetDialogFragment.setTransparentBackground() {
        dialog?.apply {
            setOnShowListener {
                val bottomSheet =
                    findViewById<View?>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet?.setBackgroundResource(android.R.color.transparent)
            }
        }
    }
}