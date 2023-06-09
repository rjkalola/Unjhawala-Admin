package com.unjhawalateaadmin.dashboard.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imateplus.utilities.callback.DialogButtonClickListener
import com.imateplus.utilities.utils.AlertDialogHelper
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.dashboard.data.ui.adapter.TeaGardenListAdapter
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.ActivityTeaGardenListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.kitek.rvswipetodelete.SwipeToDeleteCallback

class TeaGardenActivity : BaseActivity(), View.OnClickListener, SelectItemListener,
    DialogButtonClickListener {
    private lateinit var binding: ActivityTeaGardenListBinding
    private lateinit var mContext: Context
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private var adapter: TeaGardenListAdapter? = null
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
    private var swipeToDeleteCallback: SwipeToDeleteCallback? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(resources.getColor(R.color.colorAccentLight))
        mContext = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tea_garden_list)
        mContext = this
        setupToolbar(getString(R.string.tea_garden), true)
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
        binding.recyclerView.visibility = View.VISIBLE
        binding.txtEmptyPlaceHolder.visibility = View.GONE
        binding.recyclerView.setHasFixedSize(true)
        adapter = TeaGardenListAdapter(mContext, this)
        binding.recyclerView.adapter = adapter
        val linearLayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        enableSwipeToDeleteAndUndo()
//            recyclerViewScrollListener(linearLayoutManager)
//        } else {
//            binding.rvUsers.visibility = View.GONE
//            binding.txtEmptyPlaceHolder.visibility = View.VISIBLE
//        }
    }

    private fun enableSwipeToDeleteAndUndo() {
     /*   val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter?.notifyItemChanged(viewHolder.adapterPosition)
                AlertDialogHelper.showDialog(
                    mContext,
                    "",
                    getString(R.string.msg_delete_item),
                    getString(R.string.delete),
                    getString(R.string.cancel),
                    false,
                    this@TeaGardenActivity,
                    AppConstants.DialogIdentifier.DELETE_ITEM
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)*/
    }

    override fun onPositiveButtonClicked(dialogIdentifier: Int) {

    }

    override fun onNegativeButtonClicked(dialogIdentifier: Int) {

    }

    private fun recyclerViewScrollListener(layoutManager: LinearLayoutManager) {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {

            }
        }
        return super.onOptionsItemSelected(item)
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