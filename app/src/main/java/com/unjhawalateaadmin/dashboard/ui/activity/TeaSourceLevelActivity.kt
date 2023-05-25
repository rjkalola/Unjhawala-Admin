package com.unjhawalateaadminadmin.dashboard.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.imateplus.utilities.utils.AlertDialogHelper
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.ui.adapter.ViewPagerAdapter
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.dashboard.data.model.TeaSourceLevelListResponse
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.ActivityTeaSourceLevelListBinding
import com.unjhawalateaadminadmin.dashboard.data.ui.fragment.TeaSourceLevelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class TeaSourceLevelActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTeaSourceLevelListBinding
    private lateinit var mContext: Context
    private lateinit var pagerAdapter: ViewPagerAdapter
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private lateinit var teaSourceLevelListResponse: TeaSourceLevelListResponse

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(resources.getColor(R.color.colorAccentLight))
        mContext = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tea_source_level_list)
        mContext = this
        setupToolbar(getString(R.string.tea_sources), true)
        getTeaSourceConfigurationResponseObservers()
        setupViewPager(binding.viewPager)

        showCustomProgressDialog(binding.progressBarView.routProgress)
        loadData()
    }

    override fun onClick(v: View) {
        when (v.id) {
//            R.id.routCall -> {
//
//            }

        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFrag(
            TeaSourceLevelFragment.newInstance(AppConstants.Type.TEA_SOURCE_LEVEL_1),
            getString(R.string.level_1)
        )
        pagerAdapter.addFrag(
            TeaSourceLevelFragment.newInstance(AppConstants.Type.TEA_SOURCE_LEVEL_2),
            getString(R.string.level_2)
        )
        pagerAdapter.addFrag(
            TeaSourceLevelFragment.newInstance(AppConstants.Type.TEA_SOURCE_LEVEL_3),
            getString(R.string.level_3)
        )
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = 3
        binding.tabs.setupWithViewPager(viewPager)
    }

    private fun getTeaSourceConfigurationResponseObservers() {
        dashboardViewModel.teaSourceLevelListResponse.observe(this) { response ->
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
                        teaSourceLevelListResponse = response
                        refreshData()
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    fun loadData() {
        dashboardViewModel.getTeaSourceConfigurationList()
    }

    fun refreshData() {
        for (i in pagerAdapter.getmFragmentList().indices) {
            if (pagerAdapter.getmFragmentList()[i] is TeaSourceLevelFragment)
                (pagerAdapter.getmFragmentList()[i] as TeaSourceLevelFragment).setData(
                    teaSourceLevelListResponse
                )
        }
    }
}