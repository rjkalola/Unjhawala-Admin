package com.unjhawalateaadminadmin.dashboard.data.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.imateplus.utilities.callback.DialogButtonClickListener
import com.imateplus.utilities.utils.AlertDialogHelper
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.ui.fragment.BaseFragment
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.dashboard.callback.AddTeaSourceItemListener
import com.unjhawalateaadmin.dashboard.data.model.TeaSourceLevelInfo
import com.unjhawalateaadmin.dashboard.data.model.TeaSourceLevelListResponse
import com.unjhawalateaadmin.dashboard.data.ui.adapter.TeaSourceLevelAdapter
import com.unjhawalateaadmin.dashboard.ui.dialog.AddTeaSourceLevelDialog
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.FragmentTeaSourceLevelBinding
import com.unjhawalateaadminadmin.dashboard.ui.activity.TeaSourceLevelActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class TeaSourceLevelFragment : BaseFragment(), View.OnClickListener, SelectItemListener,
    AddTeaSourceItemListener, DialogButtonClickListener {
    private lateinit var binding: FragmentTeaSourceLevelBinding
    private lateinit var mContext: Context
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private var adapter: TeaSourceLevelAdapter? = null
    private var level: Int = 0
    private var selectedPosition: Int = 0
    private lateinit var teaSourceLevelListResponse: TeaSourceLevelListResponse

    companion object {
        fun newInstance(
            level: Int
        ): TeaSourceLevelFragment {
            val bundle = Bundle()
            bundle.putInt(
                AppConstants.IntentKey.TEA_SOURCE_LEVEL,
                level
            )
            val fragment = TeaSourceLevelFragment()
            fragment.arguments = bundle
            return fragment
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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tea_source_level, container, false)
        mContext = requireActivity()
        binding.routNewOrder.shrink()
        editDeleteResponseObservers()
        getIntentData()

        binding.routNewOrder.setOnClickListener {
            showAddTeaSourceDialog(null)
        }

        return binding.root
    }

    private fun getIntentData() {
        if (arguments != null) {
            level = arguments?.getInt(AppConstants.IntentKey.TEA_SOURCE_LEVEL)!!
//            showCustomProgressDialog(binding.progressBarView.routProgress)
//            loadData(false, false, false, false, false)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
//            R.id.routRetailer -> moveActivity(
//                mContext,
//                RetailersListActivity::class.java,
//                false,
//                false,
//                null
//            )
        }
    }

    fun setData(data: TeaSourceLevelListResponse) {
        teaSourceLevelListResponse = data
        when (level) {
            AppConstants.Type.TEA_SOURCE_LEVEL_1 -> {
                setAdapter(data.firstLevels)
            }
            AppConstants.Type.TEA_SOURCE_LEVEL_2 -> {
                setAdapter(data.secondLevels)
            }
            AppConstants.Type.TEA_SOURCE_LEVEL_3 -> {
                setAdapter(data.thirdLevels)
            }
        }
    }

    private fun setAdapter(list: MutableList<TeaSourceLevelInfo>?) {
        if (list != null && list.size > 0) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.txtEmptyPlaceHolder.visibility = View.GONE
            binding.recyclerView.setHasFixedSize(true)
            adapter = TeaSourceLevelAdapter(mContext, list, level, this)
            binding.recyclerView.adapter = adapter
            val linearLayoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.layoutManager = linearLayoutManager
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.txtEmptyPlaceHolder.visibility = View.VISIBLE
        }
    }

    private fun editDeleteResponseObservers() {
        dashboardViewModel.editDeleteTeaSource.observe(requireActivity()) { response ->
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
                        if (activity is TeaSourceLevelActivity) (activity as TeaSourceLevelActivity?)!!.loadData()
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    override fun onSelectItem(position: Int, action: Int, productType: Int) {
        if (action == AppConstants.Action.EDIT_TEA_SOURCE) {
            showAddTeaSourceDialog(adapter!!.list[position])
        } else if (action == AppConstants.Action.DELETE_TEA_SOURCE) {
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
            showProgressDialog(mContext, "")
            dashboardViewModel.deleteTeaSource(adapter!!.list[selectedPosition]._id!!)
        }
    }

    override fun onNegativeButtonClicked(dialogIdentifier: Int) {

    }

    override fun onAddTeaSource(level: Int, info: TeaSourceLevelInfo) {
        showProgressDialog(mContext, "")
        dashboardViewModel.storeTeaSourceResponse(info)
    }

    fun showAddTeaSourceDialog(
        info: TeaSourceLevelInfo?
    ) {
        val addProductQuantityDialog =
            AddTeaSourceLevelDialog.newInstance(
                mContext,
                info,
                teaSourceLevelListResponse,
                level,
                this
            )
        addProductQuantityDialog.setCancelable(false)
        addProductQuantityDialog.show()
    }
}