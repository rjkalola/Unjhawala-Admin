package com.unjhawalateaadmin.dashboard.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.unjhawalateaadmin.dashboard.callback.AddAvailableTeaSampleListener
import com.unjhawalateaadmin.dashboard.data.model.AvailableTeaSampleInfo
import com.unjhawalateaadmin.dashboard.data.model.AvailableTeaSampleListResponse
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleInfo
import com.unjhawalateaadmin.dashboard.data.ui.adapter.AvailableTeaSampleListAdapter
import com.unjhawalateaadmin.dashboard.ui.dialog.AddTeaSampleQuantityDialog
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.ActivityAvailableTeaSampleListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.parceler.Parcels


class AvailableTeaSampleActivity : BaseActivity(), View.OnClickListener, SelectItemListener,
    AddAvailableTeaSampleListener {
    private lateinit var binding: ActivityAvailableTeaSampleListBinding
    private lateinit var mContext: Context
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private var adapter: AvailableTeaSampleListAdapter? = null
    var visibleItemCount = 0
    var totalItemCount = 0
    var pastVisibleItems = 0
    var selectedPosition = 0
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_available_tea_sample_list)
        mContext = this
        setupToolbar(getString(R.string.available_tea_sample), true)
        teaSampleListResponseObservers()

        loadData(true, false, false)

        binding.txtViewAll.setOnClickListener {
            adapter?.filter("")
            binding.edtSearch.setText("")
            val intent = Intent(mContext, AvailableTeaSampleCartActivity::class.java)
            val data = AvailableTeaSampleListResponse()
            data.Data = adapter!!.list

            val bundle = Bundle()
            bundle.putParcelable(
                AppConstants.IntentKey.AVAILABLE_TEA_SAMPLE_DATA,
                Parcels.wrap<AvailableTeaSampleListResponse>(data)
            )
            intent.putExtras(bundle)
            cartResultActivity.launch(intent)
        }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                adapter?.filter(s.toString())
            }
        })
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

        dashboardViewModel.getAvailableTeaSampleList(
            search,
        )
    }

    private fun setAdapter(list: MutableList<AvailableTeaSampleInfo>?) {
        if (list != null && list.size > 0) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.txtEmptyPlaceHolder.visibility = View.GONE
            binding.recyclerView.setHasFixedSize(true)
            adapter = AvailableTeaSampleListAdapter(mContext, list, this)
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
        dashboardViewModel.mAvailableTeaSampleListResponse.observe(this) { response ->
            hideCustomProgressDialog(binding.progressBarView.routProgress)
            binding.loadMore.visibility = View.GONE
//            binding.progressSearchUser.visibility = View.GONE
            try {
                if (response == null) {
                    AlertDialogHelper.showDialog(
                        mContext, null,
                        mContext.getString(R.string.error_unknown), mContext.getString(R.string.ok),
                        null, false, null, 0
                    )
                } else {
                    if (response.IsSuccess) {
//                        binding.edtSearch.setText("")
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

    override fun onSelectItem(position: Int, action: Int, productType: Int) {
//        showOrderHistoryItemsDialog(adapter!!.list[position])
        if (action == AppConstants.Action.ADD_TEA_SAMPLE_Quantity) {
            selectedPosition = position
            showAddTeaSampleQuantityDialog(adapter!!.list[position])
        }
    }

    fun showAddTeaSampleQuantityDialog(
        info: AvailableTeaSampleInfo
    ) {
        val addTeaSampleQuantityDialog =
            AddTeaSampleQuantityDialog.newInstance(
                mContext,
                info,
                this
            )
        addTeaSampleQuantityDialog.setCancelable(false)
        addTeaSampleQuantityDialog.show()
    }

    override fun onAddAvailableTeaSample(info: AvailableTeaSampleInfo) {
        info.selected = true
        adapter!!.list[selectedPosition] = info
        adapter!!.notifyDataSetChanged()
        checkCartQuantity()
    }

    private fun checkCartQuantity() {
        var isSelected = false;
        if (adapter != null) {
            for (info in adapter!!.list) {
                if (info.selected!!)
                    isSelected = true
            }
        }
        if (isSelected)
            binding.routViewCart.visibility = View.VISIBLE
        else
            binding.routViewCart.visibility = View.GONE
    }

    var cartResultActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null
                && result.resultCode == Activity.RESULT_OK
            ) {
                val data = result.data
                if (data != null) {
                    val details = Parcels.unwrap<AvailableTeaSampleListResponse?>(
                        data?.getParcelableExtra(AppConstants.IntentKey.AVAILABLE_TEA_SAMPLE_DATA)
                    )
                    setAdapter(details.Data)
                    checkCartQuantity()
                }
            }
        }


}