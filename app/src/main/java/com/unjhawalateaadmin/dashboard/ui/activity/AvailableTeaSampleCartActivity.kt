package com.unjhawalateaadmin.dashboard.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imateplus.utilities.callback.DialogButtonClickListener
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
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleTestingInfo
import com.unjhawalateaadmin.dashboard.data.ui.adapter.AvailableTeaSampleCartListAdapter
import com.unjhawalateaadmin.dashboard.data.ui.adapter.AvailableTeaSampleListAdapter
import com.unjhawalateaadmin.dashboard.ui.dialog.AddTeaSampleQuantityDialog
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.ActivityAvailableTeaSampleCartListBinding
import com.unjhawalateaadmin.databinding.ActivityAvailableTeaSampleListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.parceler.Parcels


class AvailableTeaSampleCartActivity : BaseActivity(), View.OnClickListener, SelectItemListener,
    DialogButtonClickListener {
    private lateinit var binding: ActivityAvailableTeaSampleCartListBinding
    private lateinit var mContext: Context
    private var adapter: AvailableTeaSampleCartListAdapter? = null
    var selectedPosition = 0
    private var isUpdated = false
    private lateinit var data: AvailableTeaSampleListResponse

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(resources.getColor(R.color.colorAccentLight))
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_available_tea_sample_cart_list)
        mContext = this
        setupToolbar(getString(R.string.available_tea_sample), true)

        getIntentData()
        
        binding.txtConfirm.setOnClickListener {
            val intent = Intent(mContext, AddAvailableTeaSampleTestingActivity::class.java)
            val data = AvailableTeaSampleListResponse()
            data.Data = adapter!!.list

            val bundle = Bundle()
            bundle.putParcelable(
                AppConstants.IntentKey.AVAILABLE_TEA_SAMPLE_DATA,
                Parcels.wrap<AvailableTeaSampleListResponse>(data)
            )
            intent.putExtras(bundle)
            addDetailsResultActivity.launch(intent)
        }
    }

    private fun getIntentData() {
        if (intent.extras != null && intent.hasExtra(AppConstants.IntentKey.AVAILABLE_TEA_SAMPLE_DATA)) {
            data = Parcels.unwrap<AvailableTeaSampleListResponse?>(
                intent?.getParcelableExtra(AppConstants.IntentKey.AVAILABLE_TEA_SAMPLE_DATA)
            )
            setAdapter(data.Data)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txtConfirm -> {

            }
        }
    }

    private fun setAdapter(list: MutableList<AvailableTeaSampleInfo>?) {
        if (list != null && list.size > 0) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.btnConfirm.visibility = View.VISIBLE
            binding.txtEmptyPlaceHolder.visibility = View.GONE
            binding.recyclerView.setHasFixedSize(true)
            adapter = AvailableTeaSampleCartListAdapter(mContext, list, this)
            binding.recyclerView.adapter = adapter
            val linearLayoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.layoutManager = linearLayoutManager
//            recyclerViewScrollListener(linearLayoutManager)
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.btnConfirm.visibility = View.GONE
            binding.txtEmptyPlaceHolder.visibility = View.VISIBLE
        }
    }

    override fun onSelectItem(position: Int, action: Int, productType: Int) {
        if (action == AppConstants.Action.DELETE_TEA_SAMPLE_Quantity) {
            selectedPosition = position
            AlertDialogHelper.showDialog(
                mContext,
                "",
                getString(R.string.msg_delete_item),
                getString(R.string.yes),
                getString(R.string.no),
                false,
                this,
                AppConstants.DialogIdentifier.DELETE_ITEM
            )
        }
    }

    override fun onPositiveButtonClicked(dialogIdentifier: Int) {
        if (dialogIdentifier == AppConstants.DialogIdentifier.DELETE_ITEM) {
            isUpdated = true
            adapter!!.list[selectedPosition].selected = false
            adapter!!.notifyDataSetChanged()
            checkCartQuantity()
        }
    }

    override fun onNegativeButtonClicked(dialogIdentifier: Int) {

    }

    private fun checkCartQuantity() {
        var isSelected = false;
        if (adapter != null) {
            for (info in adapter!!.list) {
                if (info.selected!!)
                    isSelected = true
            }
        }
        if (!isSelected) {
            binding.recyclerView.visibility = View.GONE
            binding.btnConfirm.visibility = View.GONE
            binding.txtEmptyPlaceHolder.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.btnConfirm.visibility = View.VISIBLE
            binding.txtEmptyPlaceHolder.visibility = View.GONE
        }

    }

    var addDetailsResultActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null
                && result.resultCode == Activity.RESULT_OK
            ) {

            }
        }

    override fun onBackPressed() {
        val intent = Intent()
        val data = AvailableTeaSampleListResponse()
        data.Data = adapter!!.list
        val bundle = Bundle()
        bundle.putParcelable(
            AppConstants.IntentKey.AVAILABLE_TEA_SAMPLE_DATA,
            Parcels.wrap<AvailableTeaSampleListResponse>(data)
        )
        intent.putExtras(bundle)
        if (isUpdated)
            setResult(Activity.RESULT_OK, intent)
        finish()
    }
}