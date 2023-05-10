package com.unjhawalateaadmin.dashboard.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.dashboard.data.ui.adapter.TeaConfigurationAdapter
import com.unjhawalateaadmin.dashboard.data.ui.adapter.TeaSamplesAdapter
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.ActivityTeaConfigurationListBinding
import com.unjhawalateaadmin.databinding.ActivityTeaSampleListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class TeaSamplesActivity : BaseActivity(), View.OnClickListener, SelectItemListener {
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
    var loading = true
    var mIsLastPage = false
    private var isUpdated = false

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(resources.getColor(R.color.colorAccentLight))
        mContext = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tea_sample_list)
        mContext = this
        setupToolbar(getString(R.string.tea_samples), true)
//        orderHistoryResponseObservers()

//        binding.swipeRefreshLayout.setOnRefreshListener {
//            loadData(false, true, true, true, true)
//        }
//
//        loadData(true, false, false, false, false)

        setAdapter()
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
        isClearSort: Boolean,
        isClearSearch: Boolean
    ) {
        if (isProgress)
            showCustomProgressDialog(binding.progressBarView.routProgress)

        if (isClearOffset)
            offset = 0

        if (isClearFilter)
            filters = ""

        if (isClearSort)
            sort = 0

        if (isClearSearch)
            search = ""

//        dashboardViewModel.orderHistoryList(
//            AppConstants.DataLimit.USERS_LIMIT,
//            offset,
//        )
    }

//    private fun setAdapter(list: MutableList<OrderInfo>?) {
    private fun setAdapter() {
//        if (list != null && list.size > 0) {
            binding.rvUsers.visibility = View.VISIBLE
            binding.txtEmptyPlaceHolder.visibility = View.GONE
            binding.rvUsers.setHasFixedSize(true)
            adapter = TeaSamplesAdapter(mContext, this)
            binding.rvUsers.adapter = adapter
            val linearLayoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            binding.rvUsers.layoutManager = linearLayoutManager
//            recyclerViewScrollListener(linearLayoutManager)
//        } else {
//            binding.rvUsers.visibility = View.GONE
//            binding.txtEmptyPlaceHolder.visibility = View.VISIBLE
//        }
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
                                binding.loadMore.setVisibility(View.VISIBLE)
                                loadData(false, false, false, false, false)
                            }
                        }
                    }
                }
            }
        })
    }

   /* private fun orderHistoryResponseObservers() {
        dashboardViewModel.orderHistoryResponse.observe(this) { response ->
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
                        binding.edtSearch.setText("")
                        if (offset == 0) {
                            if (response.pending_count > 0) {
                                binding.routPendingView.visibility = View.VISIBLE
                                binding.txtNumberOfPendingMember.text = String.format(
                                    getString(R.string.display_number_of_pending_orders),
                                    response.pending_count.toString()
                                )
                            } else {
                                binding.routPendingView.visibility = View.GONE
                            }
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
    }*/

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
    }

   /* var pendingMemberResultActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null
                && result.resultCode == Activity.RESULT_OK
            ) {
                isUpdated = true
                loadData(true, true, true, true, true)
            }
        }
*/

}