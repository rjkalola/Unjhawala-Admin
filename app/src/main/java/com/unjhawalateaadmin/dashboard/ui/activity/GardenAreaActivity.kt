package com.unjhawalateaadmin.dashboard.ui.activity

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imateplus.utilities.callback.DialogButtonClickListener
import com.imateplus.utilities.utils.AlertDialogHelper
import com.imateplus.utilities.utils.StringHelper
import com.imateplus.utilities.utils.ToastHelper
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.dashboard.callback.AddConfigurationItemListener
import com.unjhawalateaadmin.dashboard.callback.StoreConfigurationItemPositionListener
import com.unjhawalateaadmin.dashboard.data.model.ConfigurationItemInfo
import com.unjhawalateaadmin.dashboard.data.model.TeaGardenConfigurationResponse
import com.unjhawalateaadmin.dashboard.data.model.TeaSeasonConfigurationResponse
import com.unjhawalateaadmin.dashboard.data.ui.adapter.GardenAreaListAdapter
import com.unjhawalateaadmin.dashboard.ui.dialog.AddGardenAreaDialog
import com.unjhawalateaadmin.dashboard.ui.dialog.AddTeaGardenDialog
import com.unjhawalateaadmin.dashboard.ui.dialog.AddTeaSeasonDialog
import com.unjhawalateaadmin.dashboard.ui.dialog.GardenAreaManagePositionDialog
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.ActivityGardenAreaListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.kitek.rvswipetodelete.SwipeToDeleteCallback

class GardenAreaActivity : BaseActivity(), View.OnClickListener, SelectItemListener,
    DialogButtonClickListener, AddConfigurationItemListener,
    StoreConfigurationItemPositionListener {
    private lateinit var binding: ActivityGardenAreaListBinding
    private lateinit var mContext: Context
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private var adapter: GardenAreaListAdapter? = null
    var visibleItemCount = 0
    var totalItemCount = 0
    var pastVisibleItems = 0
    var offset = 0
    var selectedPosition = 0
    var configurationType = 0
    var configurationTypeName: String = ""
    var search = ""
    var loading = true
    var mIsLastPage = false
    private var isUpdated = false
    private var swipeToDeleteCallback: SwipeToDeleteCallback? = null
    private var teaGardenConfigurationResponse: TeaGardenConfigurationResponse? = null
    private var teaSeasonConfigurationResponse: TeaSeasonConfigurationResponse? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(resources.getColor(R.color.colorAccentLight))
        mContext = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_garden_area_list)
        mContext = this

        configurationItemsListResponseObservers()
        configurationAllItemsListResponseObservers()
        storeConfigurationResponseObservers()
        deleteConfigurationResponseObservers()
        storeConfigurationItemPositionResponseObservers()
        teaGardenConfigurationResponseObservers()
        teaSeasonConfigurationResponseObservers()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.edtSearch.setText("")
            loadData(false, true, true)
        }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!binding.swipeRefreshLayout.isRefreshing) {
                    binding.progressSearchUser.visibility = View.VISIBLE
                    search = binding.edtSearch.text.toString().trim()
                    loadData(false, true, false)
                }
            }
        })

        binding.imgChangePosition.setOnClickListener(this)

        getIntentData()
    }

    private fun getIntentData() {
        if (intent.extras != null) {
            configurationType = intent.getIntExtra(AppConstants.IntentKey.CONFIGURATION_TYPE, 0)
            configurationTypeName =
                intent.getStringExtra(AppConstants.IntentKey.CONFIGURATION_TYPE_NAME)!!
            setupToolbar(configurationTypeName, true)
            loadData(true, false, false)
            if (configurationType == AppConstants.TeaConfiguration.TEA_GARDEN)
                dashboardViewModel.getTeaGardenConfigurationResponse()
            else if (configurationType == AppConstants.TeaConfiguration.TEA_SEASON)
                dashboardViewModel.getTeaSeasonConfigurationResponse()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imgChangePosition -> {
                showProgressDialog(mContext, "")
                dashboardViewModel.getConfigurationAllItemList(configurationType)
            }
        }
    }

    fun loadData(
        isProgress: Boolean,
        isClearOffset: Boolean,
        isClearSearch: Boolean
    ) {
        if (isProgress)
            showCustomProgressDialog(binding.progressBarView.routProgress)

        if (isClearOffset)
            offset = 0

        if (isClearSearch) {
            search = ""
        }

        dashboardViewModel.getConfigurationItemList(
            AppConstants.DataLimit.CONFIGURATION_ITEM_LIMIT,
            offset, search, configurationType
        )
    }

    private fun setAdapter(list: MutableList<ConfigurationItemInfo>) {
        if (list.isNotEmpty()) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.txtEmptyPlaceHolder.visibility = View.GONE
            binding.recyclerView.setHasFixedSize(true)
            adapter = GardenAreaListAdapter(mContext, list, this)
            binding.recyclerView.adapter = adapter
            val linearLayoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.layoutManager = linearLayoutManager
            recyclerViewScrollListener(linearLayoutManager)
            enableSwipeToDeleteAndUndo()
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.txtEmptyPlaceHolder.visibility = View.VISIBLE
        }
    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeHandler = object : SwipeToDeleteCallback(this,adapter?.list!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                selectedPosition = viewHolder.adapterPosition
                adapter?.notifyItemChanged(viewHolder.adapterPosition)
                AlertDialogHelper.showDialog(
                    mContext,
                    "",
                    getString(R.string.msg_delete_item),
                    getString(R.string.delete),
                    getString(R.string.cancel),
                    false,
                    this@GardenAreaActivity,
                    AppConstants.DialogIdentifier.DELETE_ITEM
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onPositiveButtonClicked(dialogIdentifier: Int) {
        if (dialogIdentifier == AppConstants.DialogIdentifier.DELETE_ITEM) {
            dashboardViewModel.deleteConfigurationItem(
                adapter?.list!![selectedPosition].id,
                configurationType
            )
            adapter?.list!!.removeAt(selectedPosition)
            adapter?.notifyItemRemoved(selectedPosition)
            adapter?.notifyItemRangeChanged(selectedPosition, adapter?.list!!.size)

        }
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
                                loadData(false, false, false)
                            }
                        }
                    }
                }
            }
        })
    }

    private fun configurationItemsListResponseObservers() {
        dashboardViewModel.mConfigurationItemListResponse.observe(this) { response ->
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
                            /* if (response.pending_count > 0) {
                                 binding.routPendingView.visibility = View.VISIBLE
                                 binding.txtNumberOfPendingMember.text = String.format(
                                     getString(R.string.display_number_of_pending_orders),
                                     response.pending_count.toString()
                                 )
                             } else {
                                 binding.routPendingView.visibility = View.GONE
                             }*/
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

    private fun configurationAllItemsListResponseObservers() {
        dashboardViewModel.mConfigurationAllItemListResponse.observe(this) { response ->
            hideCustomProgressDialog(binding.progressBarView.routProgress)
            hideProgressDialog()
            try {
                if (response == null) {
                    AlertDialogHelper.showDialog(
                        mContext, null,
                        mContext.getString(R.string.error_unknown), mContext.getString(R.string.ok),
                        null, false, null, 0
                    )
                } else {
                    if (response.IsSuccess) {
                        showManagePositionDialog(response.Data)
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun storeConfigurationResponseObservers() {
        dashboardViewModel.storeConfigurationItem.observe(this) { response ->
            hideCustomProgressDialog(binding.progressBarView.routProgress)
            try {
                if (response == null) {
                    AlertDialogHelper.showDialog(
                        mContext, null,
                        mContext.getString(R.string.error_unknown), mContext.getString(R.string.ok),
                        null, false, null, 0
                    )
                } else {
                    if (response.IsSuccess) {
                        if (selectedPosition == -1) {
                            loadData(true, true, true)
                        }
                        if (!StringHelper.isEmpty(response.Message))
                            ToastHelper.normal(
                                mContext,
                                response.Message!!,
                                Toast.LENGTH_SHORT,
                                false
                            )
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun deleteConfigurationResponseObservers() {
        dashboardViewModel.deleteConfigurationItem.observe(this) { response ->
            hideCustomProgressDialog(binding.progressBarView.routProgress)
            try {
                if (response == null) {
                    AlertDialogHelper.showDialog(
                        mContext, null,
                        mContext.getString(R.string.error_unknown), mContext.getString(R.string.ok),
                        null, false, null, 0
                    )
                } else {
                    if (response.IsSuccess) {
                        if (!StringHelper.isEmpty(response.Message))
                            ToastHelper.normal(
                                mContext,
                                response.Message!!,
                                Toast.LENGTH_SHORT,
                                false
                            )
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun storeConfigurationItemPositionResponseObservers() {
        dashboardViewModel.storeConfigurationItemPosition.observe(this) { response ->
            hideCustomProgressDialog(binding.progressBarView.routProgress)
            try {
                if (response == null) {
                    AlertDialogHelper.showDialog(
                        mContext, null,
                        mContext.getString(R.string.error_unknown), mContext.getString(R.string.ok),
                        null, false, null, 0
                    )
                } else {
                    if (response.IsSuccess) {
                        if (!StringHelper.isEmpty(response.Message))
                            ToastHelper.normal(
                                mContext,
                                response.Message!!,
                                Toast.LENGTH_SHORT,
                                false
                            )
                        loadData(true, true, true)
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun teaGardenConfigurationResponseObservers() {
        dashboardViewModel.teaGardenConfigurationResponse.observe(this) { response ->
            hideCustomProgressDialog(binding.progressBarView.routProgress)
            try {
                if (response == null) {
                    AlertDialogHelper.showDialog(
                        mContext, null,
                        mContext.getString(R.string.error_unknown), mContext.getString(R.string.ok),
                        null, false, null, 0
                    )
                } else {
                    if (response.IsSuccess) {
                        teaGardenConfigurationResponse = response
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun teaSeasonConfigurationResponseObservers() {
        dashboardViewModel.teaSeasonConfigurationResponse.observe(this) { response ->
            hideCustomProgressDialog(binding.progressBarView.routProgress)
            try {
                if (response == null) {
                    AlertDialogHelper.showDialog(
                        mContext, null,
                        mContext.getString(R.string.error_unknown), mContext.getString(R.string.ok),
                        null, false, null, 0
                    )
                } else {
                    if (response.IsSuccess) {
                        teaSeasonConfigurationResponse = response
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
        if (action == AppConstants.Action.EDIT_CONFIGURATION_ITEM) {
            selectedPosition = position
            if (configurationType == AppConstants.TeaConfiguration.TEA_GARDEN) {
                showTeaGardenDialog(adapter!!.list[position])
            } else if (configurationType == AppConstants.TeaConfiguration.TEA_SEASON) {
                showTeaSeasonDialog(adapter!!.list[position])
            } else if (configurationType == AppConstants.TeaConfiguration.TEA_SOURCE) {

            } else {
                showAddGardenAreaDialog(adapter!!.list[position])
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                selectedPosition = -1
                if (configurationType == AppConstants.TeaConfiguration.TEA_GARDEN) {
                    showTeaGardenDialog(null)
                } else if (configurationType == AppConstants.TeaConfiguration.TEA_SEASON) {
                    showTeaSeasonDialog(null)
                } else if (configurationType == AppConstants.TeaConfiguration.TEA_SOURCE) {

                } else {
                    showAddGardenAreaDialog(null)
                }


            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showAddGardenAreaDialog(
        info: ConfigurationItemInfo?
    ) {
        val addProductQuantityDialog =
            AddGardenAreaDialog.newInstance(
                mContext,
                info,
                configurationType,
                configurationTypeName,
                this
            )
        addProductQuantityDialog.setCancelable(false)
        addProductQuantityDialog.show()
    }

    fun showTeaGardenDialog(
        info: ConfigurationItemInfo?
    ) {
        if(teaGardenConfigurationResponse !=null){
            val addTeaGardenDialog =
                AddTeaGardenDialog.newInstance(
                    mContext,
                    info,
                    teaGardenConfigurationResponse,
                    configurationType,
                    configurationTypeName,
                    this
                )
            addTeaGardenDialog.setCancelable(false)
            addTeaGardenDialog.show()
        }
    }

    fun showTeaSeasonDialog(
        info: ConfigurationItemInfo?
    ) {
        if(teaSeasonConfigurationResponse !=null){
            val addTeaSeasonDialog =
                AddTeaSeasonDialog.newInstance(
                    mContext,
                    info,
                    teaSeasonConfigurationResponse,
                    configurationType,
                    configurationTypeName,
                    this
                )
            addTeaSeasonDialog.setCancelable(false)
            addTeaSeasonDialog.show()
        }
    }

    fun showManagePositionDialog(
        list: MutableList<ConfigurationItemInfo>
    ) {
        val gardenAreaManagePositionDialog =
            GardenAreaManagePositionDialog.newInstance(mContext, list, configurationType, this)
        gardenAreaManagePositionDialog.setCancelable(false)
        gardenAreaManagePositionDialog.show()
    }

    override fun onAddConfigurationItem(itemType: Int, info: ConfigurationItemInfo) {
        if (selectedPosition != -1) {
            Log.e("test", "selectedPosition:$selectedPosition")
            adapter?.updateItem(selectedPosition, info)
        }
        if (itemType == AppConstants.TeaConfiguration.TEA_GARDEN) {
            dashboardViewModel.storeTeaGardenItem(
                info.id,
                info.name,
                info.status,
                info.lu_leaf_type_id,
                info.lu_garden_area_id,
                configurationType
            )
        } else if (itemType == AppConstants.TeaConfiguration.TEA_SEASON) {
            dashboardViewModel.storeTeaSeasonItem(info)
        } else if (itemType == AppConstants.TeaConfiguration.TEA_SOURCE) {

        } else {
            dashboardViewModel.storeConfigurationItem(
                info.id,
                info.name,
                info.status,
                configurationType
            )
        }
    }

    override fun onStorePosition(itemType: Int, data: String) {
//        if (itemType == AppConstants.TeaConfiguration.TEA_GARDEN) {
//
//        } else if (itemType == AppConstants.TeaConfiguration.TEA_SEASON) {
//
//        } else if (itemType == AppConstants.TeaConfiguration.TEA_SOURCE) {
//
//        } else {
        dashboardViewModel.storeConfigurationItemPosition(
            data.replace(" ", ""),
            configurationType
        )
//        }
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