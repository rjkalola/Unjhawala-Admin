package com.unjhawalateaadmin.dashboard.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imateplus.utilities.utils.AlertDialogHelper
import com.imateplus.utilities.utils.DateFormatsConstants
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectDateRangeListener
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.dashboard.callback.OnApplyFilterListener
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleConfigurationResponse
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleInfo
import com.unjhawalateaadmin.dashboard.data.ui.adapter.TeaSamplesAdapter
import com.unjhawalateaadmin.dashboard.ui.dialog.TeaSampleFilterDialog
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.ActivityTeaSampleListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.parceler.Parcels


class TeaSamplesActivity : BaseActivity(), View.OnClickListener, SelectItemListener,
    OnApplyFilterListener, SelectDateRangeListener {
    private lateinit var binding: ActivityTeaSampleListBinding
    private lateinit var mContext: Context
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private var adapter: TeaSamplesAdapter? = null
    var visibleItemCount = 0
    var totalItemCount = 0
    var pastVisibleItems = 0
    var offset = 0
    var sort = 0
    var search = ""
    var filters = ""
    var filtersData = ""
    var startDate = ""
    var endDate = ""
    var loading = true
    var mIsLastPage = false
    private var isUpdated = false
    private var teaSampleConfigurationResponse: TeaSampleConfigurationResponse? = null
    private var listFilters: MutableList<ModuleInfo> = ArrayList()
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(resources.getColor(R.color.colorAccentLight))
        mContext = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tea_sample_list)
        mContext = this
        setupToolbar(getString(R.string.tea_samples), true)
        teaSampleListResponseObservers()
        getTeaSampleConfigurationResponseObservers()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.edtSearch.setText("")
            loadData(false, true, true)
        }

        binding.imgFilter.setOnClickListener {
            showFilterDialog()
        }

        binding.routAddItem.setOnClickListener {
            val intent = Intent(mContext, AddTeaSampleActivity::class.java)
            addTeaSampleResultActivity.launch(intent)
        }

        binding.routFilter.setOnClickListener {
            loadData(true, true, true)
        }

        binding.routDateFilter.setOnClickListener {
            startDate = ""
            endDate = ""
            binding.routDateFilter.visibility = View.GONE
            loadData(true, true, false)
        }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!binding.swipeRefreshLayout.isRefreshing) {
                    Log.e("test","afterTextChanged")
                    binding.progressSearchUser.visibility = View.VISIBLE
                    search = binding.edtSearch.text.toString().trim()
                    loadData(false, true, false)
                }
            }
        })

        loadData(true, false, false)
        dashboardViewModel.getTeaSampleConfigurationResponse()
    }

    override fun onClick(v: View) {
        when (v.id) {
//            R.id.routCall -> {
//
//            }
        }
    }

    fun loadData(
        isProgress: Boolean,
        isClearOffset: Boolean,
        isClearFilter: Boolean,
    ) {
        if (isProgress)
            showCustomProgressDialog(binding.progressBarView.routProgress)

        if (isClearOffset)
            offset = 0

        if (isClearFilter)
            clearFilter()

        dashboardViewModel.getTeaSampleList(
            AppConstants.DataLimit.USERS_LIMIT,
            offset,
            search,
            filters, startDate, endDate
        )
    }

    private fun setAdapter(list: MutableList<TeaSampleInfo>?) {
        if (list != null && list.size > 0) {
            binding.rvUsers.visibility = View.VISIBLE
            binding.txtEmptyPlaceHolder.visibility = View.GONE
            binding.rvUsers.setHasFixedSize(true)
            adapter = TeaSamplesAdapter(mContext, list, this)
            binding.rvUsers.adapter = adapter
            val linearLayoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            binding.rvUsers.layoutManager = linearLayoutManager
            recyclerViewScrollListener(linearLayoutManager)
        } else {
            binding.rvUsers.visibility = View.GONE
            binding.txtEmptyPlaceHolder.visibility = View.VISIBLE
        }
    }

    private fun recyclerViewScrollListener(layoutManager: LinearLayoutManager) {
        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = recyclerView.childCount
                    totalItemCount = (recyclerView.layoutManager as LinearLayoutManager).itemCount
                    pastVisibleItems =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            if (!mIsLastPage) {
                                loading = false
                                binding.loadMore.visibility = View.VISIBLE
                                loadData(false, false, false)
                            }
                        }
                    }
                }
            }
        })
    }

    private fun teaSampleListResponseObservers() {
        dashboardViewModel.mTeaSampleListResponse.observe(this) { response ->
            hideCustomProgressDialog(binding.progressBarView.routProgress)
            binding.swipeRefreshLayout.isRefreshing = false
            binding.loadMore.visibility = View.GONE
            binding.progressSearchUser.visibility = View.GONE
            try {
                if (response == null) {
                    AlertDialogHelper.showDialog(
                        mContext, null,
                        mContext.getString(R.string.error_unknown), mContext.getString(R.string.ok),
                        null, false, null, 0
                    )
                } else {
                    if (response.IsSuccess) {
                        if (offset == 0) {
                            setAdapter(response.Data)
                        } else if (response.Data.isNotEmpty()) {
                            if (adapter != null) {
                                adapter!!.addData(response.Data)
                                loading = true
                            }
                        } else if (response.offset == 0) {
                            loading = true
                        }
                        offset = response.offset

                        mIsLastPage = offset == 0
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun getTeaSampleConfigurationResponseObservers() {
        dashboardViewModel.teaSampleConfigurationResponse.observe(this) { response ->
            hideCustomProgressDialog(binding.progressBarView.routProgress)
            hideProgressDialog()
            try {
                if (response == null) {
                    AlertDialogHelper.showDialog(
                        mContext,
                        null,
                        mContext.getString(R.string.error_unknown),
                        mContext.getString(R.string.ok),
                        null,
                        false,
                        null,
                        0
                    )
                } else {
                    if (response.IsSuccess) {
                        teaSampleConfigurationResponse = response
                        listFilters.clear()
                        listFilters.addAll(teaSampleConfigurationResponse?.filters!!)
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    /* var resultApplyFilter =
         registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
             if (result.resultCode == Activity.RESULT_OK) {
                 val intent: Intent? = result.data
                 if (intent!!.hasExtra(AppConstants.IntentKey.FILTER_DATA)
                     && Parcels.unwrap<FiltersResponse?>(intent.getParcelableExtra(AppConstants.IntentKey.FILTER_DATA)) != null
                 ) {
                     val data =
                         Parcels.unwrap<FiltersResponse?>(intent.getParcelableExtra(AppConstants.IntentKey.FILTER_DATA))
                     filtersData = AppUtils.getFiltersResponseString(mContext, data)
                     filters = AppUtils.getFilterString(data)
                     offset = 0
                     sort = 0
                     mIsLastPage = false
                     loadData(true, false)
                 }
             }
         }*/

    override fun onSelectItem(position: Int, action: Int, productType: Int) {
//        showOrderHistoryItemsDialog(adapter!!.list[position])
        if (action == AppConstants.Action.EDIT_TEA_SAMPLE) {
            val intent = Intent(mContext, AddTeaSampleActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(
                AppConstants.IntentKey.TEA_SAMPLE_INFO,
                Parcels.wrap<TeaSampleInfo>(adapter!!.list[position])
            )
            intent.putExtras(bundle)
            addTeaSampleResultActivity.launch(intent)
        } else if (action == AppConstants.Action.ADD_TEA_SAMPLE_TESTING) {
            val intent = Intent(mContext, AddTeaSampleTestingActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(
                AppConstants.IntentKey.TEA_SAMPLE_INFO,
                Parcels.wrap<TeaSampleInfo>(adapter!!.list[position])
            )
            intent.putExtras(bundle)
            startActivity(intent)
//            addTeaSampleResultActivity.launch(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_menu, menu)
        menu!!.findItem(R.id.action_add).setIcon(R.drawable.ic_calendar_month)
        val drawable = menu.findItem(R.id.action_add).icon
        if (drawable != null) {
            drawable.mutate()
            drawable.setColorFilter(
                resources.getColor(R.color.colorPrimaryText),
                PorterDuff.Mode.SRC_ATOP
            )
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                AppUtils.showDateRangeDialog(
                    this@TeaSamplesActivity,
                    startDate,
                    endDate,
                    DateFormatsConstants.DD_MMM_YYYY_SPACE,
                    supportFragmentManager,
                    this
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showFilterDialog() {
        if (listFilters.isNotEmpty()) {
            val teaSampleFilterDialog =
                TeaSampleFilterDialog.newInstance(
                    mContext,
                    listFilters,
                    this
                )
            teaSampleFilterDialog.setCancelable(false)
//            teaSampleFilterDialog.setCanceledOnTouchOutside(true)
            teaSampleFilterDialog.show()
        }
    }

    override fun onSelectDate(startDate: String, endDate: String) {
        this.startDate = startDate
        this.endDate = endDate
        binding.routDateFilter.visibility = View.VISIBLE
        binding.txtDateFilter.text = "$startDate to $endDate"
        loadData(true, true, false)
    }

    override fun onApplyFilter(filterData: MutableList<ModuleInfo>) {
        filters = AppUtils.getFilterString(filterData);
        Log.e("test", "Filters:" + filters)
        checkFilterVisibility()
        loadData(true, true, false)
    }

    private fun checkFilterVisibility() {
        var isSelected = false
        for (i in 0 until listFilters.size) {
            for (j in 0 until listFilters[i].data.size) {
                if (listFilters[i].data[j].is_selected)
                    isSelected = true
            }
        }
        if (isSelected)
            binding.routFilter.visibility = View.VISIBLE
        else
            binding.routFilter.visibility = View.GONE
    }

    private fun clearFilter() {
        filters = ""
        for (i in 0 until listFilters.size) {
            listFilters[i].count = 0
            for (j in 0 until listFilters[i].data.size) {
                listFilters[i].data[j].is_selected = false
            }
        }
        binding.routFilter.visibility = View.GONE
    }

    var addTeaSampleResultActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null
                && result.resultCode == Activity.RESULT_OK
            ) {
                loadData(true, true, true)
            }
        }


}