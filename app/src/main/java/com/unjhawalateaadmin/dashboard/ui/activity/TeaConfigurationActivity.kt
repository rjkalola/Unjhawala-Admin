package com.unjhawalateaadmin.dashboard.ui.activity

import android.app.Activity
import android.content.Context
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imateplus.imagepickers.transformation.RoundedCornersTransformation
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import com.unjhawalateaadmin.common.data.model.SwipeItemInfo
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.SwipeAndDragHelper
import com.unjhawalateaadmin.dashboard.data.ui.adapter.GardenAreaManagePositionListAdapter
import com.unjhawalateaadmin.dashboard.data.ui.adapter.TeaConfigurationAdapter
import com.unjhawalateaadmin.dashboard.ui.dialog.GardenAreaManagePositionDialog
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.ActivityTeaConfigurationListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class TeaConfigurationActivity : BaseActivity(), View.OnClickListener, SelectItemListener {
    private lateinit var binding: ActivityTeaConfigurationListBinding
    private lateinit var mContext: Context
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private var adapter: TeaConfigurationAdapter? = null
    private var adapterSwipe: GardenAreaManagePositionListAdapter? = null
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tea_configuration_list)
        mContext = this
        setupToolbar(getString(R.string.tea_configuration), true)

        setAdapter(getConfigurationItems())
//        setSwipeAdapter()
    }

    override fun onClick(v: View) {
        when (v.id) {
//            R.id.routCall -> {
//
//            }

        }
    }

    private fun setAdapter(list: MutableList<ModuleInfo>) {
        binding.rvUsers.visibility = View.VISIBLE
        binding.txtEmptyPlaceHolder.visibility = View.GONE
        binding.rvUsers.setHasFixedSize(true)
        adapter = TeaConfigurationAdapter(mContext, list, this)
        binding.rvUsers.adapter = adapter
        val linearLayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        binding.rvUsers.layoutManager = linearLayoutManager
    }

    private fun getConfigurationItems(): MutableList<ModuleInfo> {
        val list: MutableList<ModuleInfo> = ArrayList()

        var info: ModuleInfo? = null

        info = ModuleInfo()
        info.id = AppConstants.TeaConfiguration.TEA_GARDEN_AREA
        info.name = getString(R.string.tea_garden_area)
        info.icon = R.drawable.ic_area_chart
        list.add(info)

        info = ModuleInfo()
        info.id = AppConstants.TeaConfiguration.LEAF_TYPE
        info.name = getString(R.string.leaf_type)
        info.icon = R.drawable.ic_nest_eco_leaf
        list.add(info)

        info = ModuleInfo()
        info.id = AppConstants.TeaConfiguration.TEA_GARDEN
        info.name = getString(R.string.tea_garden)
        info.icon = R.drawable.ic_yard
        list.add(info)

        info = ModuleInfo()
        info.id = AppConstants.TeaConfiguration.TEA_GRADE
        info.name = getString(R.string.tea_grade)
        info.icon = R.drawable.ic_grade
        list.add(info)

        info = ModuleInfo()
        info.id = AppConstants.TeaConfiguration.TEA_QUALITY
        info.name = getString(R.string.tea_quality)
        info.icon = R.drawable.ic_high_quality
        list.add(info)

        info = ModuleInfo()
        info.id = AppConstants.TeaConfiguration.TEA_TYPE
        info.name = getString(R.string.tea_type)
        info.icon = R.drawable.ic_content_cut
        list.add(info)

        info = ModuleInfo()
        info.id = AppConstants.TeaConfiguration.TEA_COLOR
        info.name = getString(R.string.tea_colour)
        info.icon = R.drawable.ic_invert_colors
        list.add(info)

        info = ModuleInfo()
        info.id = AppConstants.TeaConfiguration.TEA_DENSITY
        info.name = getString(R.string.tea_density)
        info.icon = R.drawable.ic_specific_gravity
        list.add(info)

        info = ModuleInfo()
        info.id = AppConstants.TeaConfiguration.TEA_SEASON
        info.name = getString(R.string.tea_season)
        info.icon = R.drawable.ic_onsen
        list.add(info)

        info = ModuleInfo()
        info.id = AppConstants.TeaConfiguration.TEA_PRODUCT_PREFERENCE
        info.name = getString(R.string.tea_product_preference)
        info.icon = R.drawable.ic_temp_preferences_eco
        list.add(info)

        info = ModuleInfo()
        info.id = AppConstants.TeaConfiguration.TEA_INWARD_BAG_TYPE
        info.name = getString(R.string.tea_inward_bag_type)
        info.icon = R.drawable.ic_shopping_bag
        list.add(info)

        info = ModuleInfo()
        info.id = AppConstants.TeaConfiguration.TEA_SOURCE
        info.name = getString(R.string.tea_source)
        info.icon = R.drawable.ic_soup_kitchen
        list.add(info)

        return list
    }

    override fun onSelectItem(position: Int, action: Int, productType: Int) {
         if (action == AppConstants.TeaConfiguration.TEA_SOURCE) {

        } else {
            val bundle = Bundle()
            bundle.putInt(AppConstants.IntentKey.CONFIGURATION_TYPE, action)
            bundle.putString(
                AppConstants.IntentKey.CONFIGURATION_TYPE_NAME,
                getConfigurationItems()[position].name
            )
            moveActivity(mContext, GardenAreaActivity::class.java, false, false, bundle)
        }
    }


    var configurationResultActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null
                && result.resultCode == Activity.RESULT_OK
            ) {

            }
        }

}