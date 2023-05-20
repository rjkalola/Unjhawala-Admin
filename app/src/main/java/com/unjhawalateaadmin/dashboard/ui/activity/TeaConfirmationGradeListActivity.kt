package com.unjhawalateaadmin.dashboard.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imateplus.utilities.utils.AlertDialogHelper
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.dashboard.data.model.AvailableTeaSampleInfo
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleInfo
import com.unjhawalateaadmin.dashboard.data.ui.adapter.TeaConfirmationGradeListAdapter
import com.unjhawalateaadmin.dashboard.data.ui.adapter.TeaConfirmationListAdapter
import com.unjhawalateaadmin.dashboard.data.ui.adapter.TeaSamplesAdapter
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.ActivityTeaConfirmationGradeListBinding
import com.unjhawalateaadmin.databinding.ActivityTeaConfirmationListBinding
import com.unjhawalateaadmin.databinding.ActivityTeaSampleListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.parceler.Parcels


class TeaConfirmationGradeListActivity : BaseActivity(), View.OnClickListener, SelectItemListener {
    private lateinit var binding: ActivityTeaConfirmationGradeListBinding
    private lateinit var mContext: Context
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private var adapter: TeaConfirmationGradeListAdapter? = null
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
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_tea_confirmation_grade_list)
        mContext = this
        setupToolbar(getString(R.string.tea_confirmed_grade_wise), true)
        teaConfirmationListResponseObservers()
        binding.routNewOrder.shrink()


//        binding.routNewOrder.setOnClickListener {
//            moveActivity(mContext, AvailableTeaSampleActivity::class.java, false, false, null)
//        }

        loadData(true, false, false)
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
            filters = ""

        dashboardViewModel.getTeaConfirmationGradeList(
        )
    }

    private fun setAdapter(list: MutableList<AvailableTeaSampleInfo>?) {
        if (list != null && list.size > 0) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.txtEmptyPlaceHolder.visibility = View.GONE
            binding.recyclerView.setHasFixedSize(true)
            adapter = TeaConfirmationGradeListAdapter(mContext, list, this)
            binding.recyclerView.adapter = adapter
            val linearLayoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.layoutManager = linearLayoutManager
//            recyclerViewScrollListener(linearLayoutManager)
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.txtEmptyPlaceHolder.visibility = View.VISIBLE
        }
    }

    private fun teaConfirmationListResponseObservers() {
        dashboardViewModel.mAvailableTeaSampleListResponse.observe(this) { response ->
            hideCustomProgressDialog(binding.progressBarView.routProgress)
            binding.loadMore.visibility = View.GONE
            try {
                if (response == null) {
                    AlertDialogHelper.showDialog(
                        mContext, null,
                        mContext.getString(R.string.error_unknown), mContext.getString(R.string.ok),
                        null, false, null, 0
                    )
                } else {
                    if (response.IsSuccess) {
                        setAdapter(response.Data)
                        /*  if (offset == 0) {
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

                          mIsLastPage = offset == 0*/
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
                Parcels.wrap<AvailableTeaSampleInfo>(adapter!!.list[position])
            )
            intent.putExtras(bundle)
            addTeaSampleResultActivity.launch(intent)
        } else if (action == AppConstants.Action.ADD_TEA_SAMPLE_TESTING) {
            val intent = Intent(mContext, AddTeaSampleTestingActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(
                AppConstants.IntentKey.TEA_SAMPLE_INFO,
                Parcels.wrap<AvailableTeaSampleInfo>(adapter!!.list[position])
            )
            intent.putExtras(bundle)
            startActivity(intent)
//            addTeaSampleResultActivity.launch(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.tea_confirmation_grade_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action -> {
                moveActivity(mContext, TeaConfirmationListActivity::class.java, true, false, null)
            }
        }
        return super.onOptionsItemSelected(item)
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