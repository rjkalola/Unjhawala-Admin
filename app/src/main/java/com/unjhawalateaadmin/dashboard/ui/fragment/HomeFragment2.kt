package com.unjhawalateaadmin.dashboard.data.ui.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.ui.adapter.ViewPagerAdapter
import com.unjhawalateaadmin.common.ui.fragment.ApproveTermsConditionsDialog
import com.unjhawalateaadmin.common.ui.fragment.BaseFragment
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.dashboard.data.model.PrivacyPolicyResponse
import com.unjhawalateaadmin.dashboard.ui.activity.*
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.FragmentHomeBinding
import com.imateplus.utilities.callback.DialogButtonClickListener
import com.imateplus.utilities.utils.AlertDialogHelper
import com.imateplus.utilities.utils.StringHelper
import com.unjhawalateaadmin.databinding.FragmentHome2Binding
import org.koin.androidx.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class HomeFragment2 : BaseFragment(), View.OnClickListener, EasyPermissions.PermissionCallbacks,
    SelectItemListener, DialogButtonClickListener {
    private lateinit var binding: FragmentHome2Binding
    private lateinit var mContext: Context
    private lateinit var pagerAdapter: ViewPagerAdapter
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private var isInitial = true

    companion object {
        fun newInstance(): HomeFragment2 {
            return HomeFragment2()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home2, container, false)
        mContext = requireActivity()
        dashboardObservers()
        privacyPolicyResponseObservers()
        approveTermsConditionsResponseObservers()
        showCustomProgressDialog(binding.progressBarView.routProgress)
        dashboardViewModel.getDashboardResponse()

        binding.txtUserName.text = AppUtils.getUserPreference(mContext)?.name

        binding.routRetailer.setOnClickListener(this)
        binding.routAgencies.setOnClickListener(this)
        binding.routTransferKg.setOnClickListener(this)
        binding.routTransferKgAgency.setOnClickListener(this)
        binding.routWorkingArea.setOnClickListener(this)
        binding.routScanQrCode.setOnClickListener(this)
        binding.routScanQrCodeAgency.setOnClickListener(this)
        binding.routScanQrCodeRetailer.setOnClickListener(this)
        binding.routToDo.setOnClickListener(this)
        binding.routCaseCalculator.setOnClickListener(this)
        binding.routMyOrder.setOnClickListener(this)
        binding.routMyOrderAgency.setOnClickListener(this)
        binding.routMyOrderRetailer.setOnClickListener(this)
        binding.routKgHistory.setOnClickListener(this)
        binding.routAccount.setOnClickListener(this)
        binding.routAccountAgency.setOnClickListener(this)
        binding.routAccountRetailer.setOnClickListener(this)
        binding.routCurrentYearHistory.setOnClickListener(this)
        binding.routPreviousYearHistory.setOnClickListener(this)
        binding.routPendingOrders.setOnClickListener(this)
        binding.routHelpRetailer.setOnClickListener(this)
        binding.routHelpAgency.setOnClickListener(this)

        binding.routNewOrder.setOnClickListener {
//            moveActivity(mContext, ProductsActivity::class.java, false, false, null)
        }

        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY -> // the delay of the extension of the FAB is set for 12 items
            if (scrollY > oldScrollY + 12 && binding.routNewOrder.isExtended) {
                binding.routNewOrder.shrink()
                if (activity is DashBoardActivity) (activity as DashBoardActivity?)!!.hideBottomTabs()
            }

            // the delay of the extension of the FAB is set for 12 items
            if (scrollY < oldScrollY - 12 && !binding.routNewOrder.isExtended) {
                binding.routNewOrder.extend()
                if (activity is DashBoardActivity) (activity as DashBoardActivity?)!!.showBottomTabs()
            }

            // if the nestedScrollView is at the first item of the list then the
            // extended floating action should be in extended state
            if (scrollY == 0) {
                binding.routNewOrder.extend()
                if (activity is DashBoardActivity) (activity as DashBoardActivity?)!!.showBottomTabs()
            }
        })

        setViewByUserType()

        return binding.root
    }

    override fun onClick(v: View) {
        when (v.id) {
           /* R.id.routRetailer -> moveActivity(
                mContext,
                RetailersListActivity::class.java,
                false,
                false,
                null
            )
            R.id.routAgencies -> moveActivity(
                mContext,
                AgenciesListActivity::class.java,
                false,
                false,
                null
            )
            R.id.routTransferKg, R.id.routTransferKgAgency -> {
                *//* moveActivity(
                     mContext,
                     TransferKgActivity::class.java,
                     false,
                     false,
                     null
                 )*//*
                val intent = Intent(mContext, TransferKgActivity::class.java)
                transferKgResultActivity.launch(intent)
            }
            R.id.routWorkingArea -> moveActivity(
                mContext,
                WorkingAreaListActivity::class.java,
                false,
                false,
                null
            )
            R.id.routScanQrCode, R.id.routScanQrCodeAgency, R.id.routScanQrCodeRetailer -> checkPermission()
            R.id.routToDo -> moveActivity(
                mContext,
                ToDoListActivity::class.java,
                false,
                false,
                null
            )
            R.id.routCaseCalculator -> moveActivity(
                mContext,
                CaseCalculatorActivity::class.java,
                false,
                false,
                null
            )
            R.id.routMyOrder, R.id.routMyOrderAgency, R.id.routMyOrderRetailer -> moveActivity(
                mContext,
                MyOrdersActivity::class.java,
                false,
                false,
                null
            )
            R.id.routKgHistory -> {
                val bundle = Bundle()
                bundle.putInt(AppConstants.IntentKey.HISTORY_TYPE, AppConstants.Type.KG_HISTORY)
                moveActivity(
                    mContext,
                    KgHistoryActivity::class.java,
                    false,
                    false,
                    bundle
                )
            }
            R.id.routCurrentYearHistory -> {
                val bundle = Bundle()
                bundle.putInt(
                    AppConstants.IntentKey.HISTORY_TYPE,
                    AppConstants.Type.CURRENT_YEAR_KG_HISTORY
                )
                moveActivity(
                    mContext,
                    KgHistoryActivity::class.java,
                    false,
                    false,
                    bundle
                )
            }
            R.id.routPreviousYearHistory -> {
                val bundle = Bundle()
                bundle.putInt(
                    AppConstants.IntentKey.HISTORY_TYPE,
                    AppConstants.Type.LAST_YEAR_KG_HISTORY
                )
                moveActivity(
                    mContext,
                    KgHistoryActivity::class.java,
                    false,
                    false,
                    bundle
                )
            }
            R.id.routAccount, R.id.routAccountAgency, R.id.routAccountRetailer -> moveActivity(
                mContext,
                MarshalBookListActivity::class.java,
                false,
                false,
                null
            )
            R.id.routPendingOrders -> moveActivity(
                mContext,
                OrdersActivity::class.java,
                false,
                false,
                null
            )
            R.id.routHelpRetailer, R.id.routHelpAgency -> moveActivity(
                mContext,
                SupportActivity::class.java,
                false,
                false,
                null
            )*/
        }
    }

    private fun setViewByUserType() {
        when (AppUtils.getUserPreference(mContext)?.member_type_id) {
            AppConstants.UserType.Dealer -> {
                binding.routQuickLinksDealer.visibility = View.VISIBLE
            }
            AppConstants.UserType.Agency -> {
                binding.routQuickLinksAgency.visibility = View.VISIBLE
                binding.txtMyNetworkTitle.visibility = View.GONE
                binding.routMyNetwork.visibility = View.GONE
            }
            AppConstants.UserType.Retailer -> {
                binding.routQuickLinksRetailer.visibility = View.VISIBLE
                binding.txtMyNetworkTitle.visibility = View.GONE
                binding.routMyNetwork.visibility = View.GONE
            }
        }
    }

    var transferKgResultActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            dashboardViewModel.getDashboardResponse()
        }

    private fun dashboardObservers() {
        dashboardViewModel.dashboardResponse.observe(requireActivity()) { response ->
            try {
                if (response == null) {
                    AlertDialogHelper.showDialog(
                        mContext, null,
                        mContext.getString(R.string.error_unknown), mContext.getString(R.string.ok),
                        null, false, null, 0
                    )
                } else {
                    if (response.IsSuccess) {
                        if (!StringHelper.isEmpty(response.available_kg))
                            binding.txtAvailableKg.text = response.available_kg
                        if (!StringHelper.isEmpty(response.current_year_kg))
                            binding.txtCurrentYearKg.text = response.current_year_kg
                        if (!StringHelper.isEmpty(response.previous_year_kg))
                            binding.txtLastYearKg.text = response.previous_year_kg
                        binding.txtTotalPendingOrder.text = response.dealer_pending_order.toString()
                        val user = AppUtils.getUserPreference(mContext);
                        user!!.total_kg = response.total_kg
                        user.first_letter = response.first_letter
                        user.accept_terms = response.accept_terms
                        user.joining_date = response.joining_date
                        AppUtils.setUserPreference(mContext, user)
                        if (response.accept_terms == 0) {
                            dashboardViewModel.getTermsConditions()
                        } else {
                            hideCustomProgressDialog(binding.progressBarView.routProgress)
                        }
                        updateAppDialog(response.app_version)
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun privacyPolicyResponseObservers() {
        dashboardViewModel.privacyPolicyResponse.observe(requireActivity()) { response ->
            hideProgressDialog()
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
                        showApproveTermsConditionsDialog(response)
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun approveTermsConditionsResponseObservers() {
        dashboardViewModel.acceptTermsConditionsResponse.observe(requireActivity()) { response ->
            hideProgressDialog()
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

                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun checkPermission() {
        if (hasPermission()) {
            moveScanQrCode()
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.msg_camera_permission),
                AppConstants.IntentKey.CAMERA_PERMISSION,
                Manifest.permission.CAMERA
            )
        }
    }

    private fun hasPermission(): Boolean {
        return EasyPermissions.hasPermissions(
            requireActivity(),
            Manifest.permission.CAMERA
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        moveScanQrCode()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    private fun moveScanQrCode() {
//        moveActivity(mContext, ScanQrCodeActivity::class.java, false, false, null)
    }

    private fun showApproveTermsConditionsDialog(data: PrivacyPolicyResponse) {
        val fm = (mContext as BaseActivity).supportFragmentManager
        val dialog =
            ApproveTermsConditionsDialog(requireActivity(), this, data)
        dialog.isCancelable = false
        dialog.show(fm, "ApproveTermsConditionsDialog")
    }

    override fun onSelectItem(position: Int, action: Int, productType: Int) {
        if (action == AppConstants.Action.APPROVE_TERMS_CONDITIONS) {
//            dashboardViewModel.acceptTermsConditions()
        }
    }

    fun refreshDashboardData() {
        if (!isInitial) {
            dashboardViewModel.getDashboardResponse()
        } else {
            isInitial = false
        }
    }

    fun updateAppDialog(versionCode: Int) {
       /* if (BuildConfig.VERSION_CODE < versionCode) {
            AlertDialogHelper.showDialog(
                mContext, getString(R.string.title_update_app),
                getString(R.string.msg_update_app), mContext.getString(R.string.ok),
                null, false, this, AppConstants.DialogIdentifier.UPDATE_APP
            )
        }*/
    }
    
    override fun onPositiveButtonClicked(dialogIdentifier: Int) {
        if (dialogIdentifier == AppConstants.DialogIdentifier.UPDATE_APP) {
            AppUtils.openPlayStore(mContext)
        }
    }

    override fun onNegativeButtonClicked(dialogIdentifier: Int) {

    }
}