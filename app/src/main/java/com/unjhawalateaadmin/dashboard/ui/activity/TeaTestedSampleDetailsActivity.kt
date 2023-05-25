package com.unjhawalateaadmin.dashboard.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.imateplus.imagepickers.utils.Constant
import com.imateplus.utilities.utils.AlertDialogHelper
import com.imateplus.utilities.utils.StringHelper
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.dashboard.data.ui.adapter.SampleTestDetailsItemsListAdapter
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.ActivityTestedSampleDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class TeaTestedSampleDetailsActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTestedSampleDetailsBinding
    private lateinit var mContext: Context
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private var id: String = ""

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(resources.getColor(R.color.colorAccentLight))
        mContext = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tested_sample_details)
        mContext = this
        setupToolbar(getString(R.string.tested_sample), true)
        testedSampleDetailsResponseObservers()
        getIntentData()
    }

    private fun getIntentData() {
        if (intent.extras != null && intent.hasExtra(AppConstants.IntentKey.ID)) {
            id = intent.getStringExtra(AppConstants.IntentKey.ID)!!
            loadData(true)
        }
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
    ) {
        if (isProgress)
            showCustomProgressDialog(binding.progressBarView.routProgress)

        dashboardViewModel.teaTestingDataDetails(
            id
        )
    }

    private fun setCustomItemsAdapter(list: MutableList<ModuleInfo>) {
//    private fun setCustomItemsAdapter() {
        if (list.isNotEmpty()) {
            binding.rvCustomItems.visibility = View.VISIBLE
            binding.rvCustomItems.setHasFixedSize(true)
            val adapter = SampleTestDetailsItemsListAdapter(mContext, list)
            binding.rvCustomItems.adapter = adapter
            val layoutManager =
                GridLayoutManager(mContext, 2)
            binding.rvCustomItems.layoutManager = layoutManager
        } else {
            binding.rvCustomItems.visibility = View.GONE
        }
    }

    private fun testedSampleDetailsResponseObservers() {
        dashboardViewModel.mTeaTestedSampleDetailsResponse.observe(this) { response ->
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
                        binding.info = response.Data
                        if (!StringHelper.isEmpty(response.Data.attachment)) {
                            binding.txtChooseAttachment.visibility = View.GONE
                            binding.imgAttachmentTitle.visibility = View.VISIBLE
                            binding.routAttachment.visibility = View.VISIBLE

                            if (response.Data.attachment!!.endsWith(".pdf")) {
                                binding.imgPdf.visibility = View.VISIBLE
                                binding.imgJpg.visibility = View.GONE
                            } else {
                                binding.imgPdf.visibility = View.GONE
                                binding.imgJpg.visibility = View.VISIBLE
                                AppUtils.setImage(
                                    mContext, response.Data.attachment, binding.imgJpg,
                                    Constant.ImageScaleType.CENTER_CROP
                                )
                            }

                        } else {
                            binding.imgAttachmentTitle.visibility = View.GONE
                            binding.routAttachment.visibility = View.GONE
                        }

                        setCustomItemsAdapter(response.Data.details)
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }


}