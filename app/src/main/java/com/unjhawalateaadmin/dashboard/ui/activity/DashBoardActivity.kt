package com.unjhawalateaadmin.dashboard.ui.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.google.firebase.messaging.FirebaseMessaging
import com.unjhawalateaadmin.MyApplication
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.authentication.ui.activity.LoginActivity
import com.unjhawalateaadmin.authentication.ui.viewmodel.AuthenticationViewModel
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.ui.adapter.ViewPagerAdapter
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.common.utils.ExpandCollapseAnimation
import com.unjhawalateaadmin.dashboard.data.ui.fragment.HomeFragment
import com.unjhawalateaadmin.databinding.ActivityDashboardBinding
import com.unjhawalateaadmin.databinding.ContentDashboardBinding
import com.imateplus.utilities.callback.DialogButtonClickListener
import com.imateplus.utilities.utils.AlertDialogHelper
import com.imateplus.utilities.utils.StringHelper
import com.imateplus.utilities.utils.ViewPagerDisableSwipe
import org.koin.androidx.viewmodel.ext.android.viewModel


class DashBoardActivity : BaseActivity(), View.OnClickListener, DialogButtonClickListener {
    private lateinit var binding: ActivityDashboardBinding
    private var bindingContent: ContentDashboardBinding? = null
    private lateinit var mContext: Context;
    private lateinit var pagerAdapter: ViewPagerAdapter
    private var selectedTabIndex: Int = 0
    private var doubleBackToExitPressedOnce = false
    private val authenticationViewModel: AuthenticationViewModel by viewModel()
    private lateinit var txtLogout: TextView
    private lateinit var txtUserNameLetter: TextView
    private lateinit var routUserProfile: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        bindingContent = DataBindingUtil.bind(binding.appBarLayout.contentDashboard.root)
        setStatusBarColor(resources.getColor(R.color.colorAccentDark))
        mContext = this
        setupToolbar(getString(R.string.dashboard), false)
        setupViewPager(bindingContent!!.viewPager);
        storeDeviceTokenObservers()
        routUserProfile = findViewById(R.id.routUserProfile)
//        routUserProfile.visibility = View.VISIBLE
        bindingContent!!.routTabsView.routHomeTab.setOnClickListener(this)
        bindingContent!!.routTabsView.routReport.setOnClickListener(this)
        bindingContent!!.routTabsView.routAbout.setOnClickListener(this)
        routUserProfile.setOnClickListener(this)
        txtLogout = findViewById(R.id.txtLogout)
        txtUserNameLetter = findViewById(R.id.txtUserNameLetter)
//        txtLogout.visibility = View.VISIBLE
        txtLogout.setOnClickListener(this)

//        getFcmToken()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.routHomeTab -> setupTab(0)
            R.id.routReport -> setupTab(1)
            R.id.routAbout -> setupTab(2)
            R.id.txtLogout -> logoutDialog()
          /*  R.id.routUserProfile -> moveActivity(
                mContext,
                ProfileActivity::class.java,
                false,
                false,
                null
            )*/
        }
    }

    private fun setupViewPager(viewPager: ViewPagerDisableSwipe) {
        viewPager.setPagingEnabled(false)
        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFrag(HomeFragment.newInstance(), "")
//        pagerAdapter.addFrag(HomeFragment.newInstance(), "")
//        pagerAdapter.addFrag(HomeFragment.newInstance(), "")
        viewPager.adapter = pagerAdapter
        setupTab(selectedTabIndex)
        viewPager.offscreenPageLimit = 3

    }

    private fun setupTab(position: Int) {
        selectedTabIndex = position
        resetTabColor()
//        bindingContent!!.viewPager.currentItem = position

        when (position) {
            0 -> {
                bindingContent!!.routTabsView.imgHomeTab.setColorFilter(resources.getColor(R.color.colorActiveIcon))
                bindingContent!!.routTabsView.imgHomeTab.setBackgroundResource(R.drawable.ic_dashboard_tab_icon_bg)
            }
            1 -> {
                bindingContent!!.routTabsView.imgReport.setColorFilter(resources.getColor(R.color.colorActiveIcon))
                bindingContent!!.routTabsView.imgReport.setBackgroundResource(R.drawable.ic_dashboard_tab_icon_bg)
            }
            2 -> {
                bindingContent!!.routTabsView.imgAbout.setColorFilter(resources.getColor(R.color.colorActiveIcon))
                bindingContent!!.routTabsView.imgAbout.setBackgroundResource(R.drawable.ic_dashboard_tab_icon_bg)
            }
        }

    }

    private fun resetTabColor() {
        bindingContent!!.routTabsView.imgHomeTab.setColorFilter(resources.getColor(R.color.colorDeActiveIcon))
        bindingContent!!.routTabsView.imgHomeTab.setBackgroundColor(resources.getColor(R.color.transparent))
        bindingContent!!.routTabsView.imgReport.setColorFilter(resources.getColor(R.color.colorDeActiveIcon))
        bindingContent!!.routTabsView.imgReport.setBackgroundColor(resources.getColor(R.color.transparent))
        bindingContent!!.routTabsView.imgAbout.setColorFilter(resources.getColor(R.color.colorDeActiveIcon))
        bindingContent!!.routTabsView.imgAbout.setBackgroundColor(resources.getColor(R.color.transparent))
    }

    private fun storeDeviceTokenObservers() {
        authenticationViewModel.storeDeviceTokenResponse.observe(this) { response ->
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

                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isComplete) {
                val firebaseToken = it.result.toString()
                Log.e("test", "token $firebaseToken");
                authenticationViewModel.storeDeviceTokenResponse(firebaseToken)
            }
        }
    }

//    public fun showBottomTabs() {
//        if (!bindingContent!!.routTabsView.routTabsView.isVisible)
//            bindingContent!!.routTabsView.routTabsView.visibility = View.VISIBLE;
//    }
//
//    public fun hideBottomTabs() {
//        if (bindingContent!!.routTabsView.routTabsView.isVisible)
//            bindingContent!!.routTabsView.routTabsView.visibility = View.GONE;
//    }

    fun hideBottomTabs() {
        if (bindingContent!!.routTabsView.routTabsView.isVisible)
            ExpandCollapseAnimation.collapse(bindingContent!!.routTabsView.routTabsView)
    }

    fun showBottomTabs() {
        if (!bindingContent!!.routTabsView.routTabsView.isVisible)
            ExpandCollapseAnimation.expand(bindingContent!!.routTabsView.routTabsView)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.msg_exit_app), Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun logoutDialog() {
        AlertDialogHelper.showDialog(
            mContext,
            "",
            getString(R.string.logout_msg),
            getString(R.string.yes),
            getString(R.string.no),
            false,
            this,
            AppConstants.DialogIdentifier.LOGOUT
        )
    }

    override fun onPositiveButtonClicked(dialogIdentifier: Int) {
        if (dialogIdentifier == AppConstants.DialogIdentifier.LOGOUT) {
            MyApplication().clearData()
            moveActivity(
                mContext,
                LoginActivity::class.java, true, true, null
            )
        }
    }

    override fun onNegativeButtonClicked(dialogIdentifier: Int) {

    }

    private fun setUserData() {
//        val user = AppUtils.getUserPreference(mContext)!!
//        if (!StringHelper.isEmpty(user.first_letter))
//            txtUserNameLetter.text = user.first_letter
    }

    override fun onResume() {
        super.onResume()
        setUserData()
       /* for (i in 0 until pagerAdapter.getmFragmentList().size) {
            if (pagerAdapter.getmFragmentList()[i] is HomeFragment) {
                (pagerAdapter.getmFragmentList()[i] as HomeFragment).refreshDashboardData()
            }
        }*/
    }
}

