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
import org.koin.androidx.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class HomeFragment : BaseFragment(), View.OnClickListener, EasyPermissions.PermissionCallbacks,
    SelectItemListener, DialogButtonClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mContext: Context
    private lateinit var pagerAdapter: ViewPagerAdapter
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private var isInitial = true

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        mContext = requireActivity()
        dashboardObservers()
        privacyPolicyResponseObservers()
        approveTermsConditionsResponseObservers()
        /* showCustomProgressDialog(binding.progressBarView.routProgress)
         dashboardViewModel.getDashboardResponse()*/

//        binding.txtUserName.text = AppUtils.getUserPreference(mContext)?.name

        binding.routTeaConfiguration.setOnClickListener(this)
        binding.routTeaSample.setOnClickListener(this)
        binding.routTeaConfirmation.setOnClickListener(this)
        binding.routTeaTesting.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.routTeaConfiguration -> moveActivity(
                mContext,
                TeaConfigurationActivity::class.java,
                false,
                false,
                null
            )
            R.id.routTeaSample -> moveActivity(
                mContext,
                TeaSamplesActivity::class.java,
                false,
                false,
                null
            )
            R.id.routTeaConfirmation -> moveActivity(
                mContext,
                TeaConfirmationListActivity::class.java,
                false,
                false,
                null
            )
            R.id.routTeaTesting -> moveActivity(
                mContext,
                TeaTestedSampleListActivity::class.java,
                false,
                false,
                null
            )

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