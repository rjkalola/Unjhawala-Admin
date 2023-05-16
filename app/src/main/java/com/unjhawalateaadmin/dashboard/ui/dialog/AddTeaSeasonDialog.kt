package com.unjhawalateaadmin.dashboard.ui.dialog

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.imateplus.utilities.utils.StringHelper
import com.imateplus.utilities.utils.ValidationUtil
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import com.unjhawalateaadmin.dashboard.callback.AddConfigurationItemListener
import com.unjhawalateaadmin.dashboard.data.model.ConfigurationItemInfo
import com.unjhawalateaadmin.dashboard.data.model.TeaGardenConfigurationResponse
import com.unjhawalateaadmin.dashboard.data.model.TeaSeasonConfigurationResponse
import com.unjhawalateaadmin.dashboard.data.model.TeaSeasonQualityInfo
import com.unjhawalateaadmin.dashboard.data.ui.adapter.TeaSeasonQualityListAdapter
import com.unjhawalateaadmin.databinding.DialogAddTeaSeasonBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class AddTeaSeasonDialog(mContext: Context?) :
    BottomSheetDialog(mContext!!, R.style.CustomBottomSheetDialogTheme) {
    private lateinit var binding: DialogAddTeaSeasonBinding

    companion
    object {
        private var mListener: AddConfigurationItemListener? = null
        private lateinit var mContext: Context
        private var itemType: Int = 0
        private var itemName: String = ""
        private var mConfigurationItemInfo: ConfigurationItemInfo? = null
        private var teaSeasonConfigurationResponse: TeaSeasonConfigurationResponse? = null
        private lateinit var selectItemBottomSheetDialog: SelectItemBottomSheetDialog
        private var adapter: TeaSeasonQualityListAdapter? = null

        fun newInstance(
            context: Context?,
            info: ConfigurationItemInfo?,
            teaSeasonConfigurationResponse: TeaSeasonConfigurationResponse?,
            itemType: Int,
            itemName: String,
            listener: AddConfigurationItemListener
        ): AddTeaSeasonDialog {
            this.mListener = listener
            this.mContext = context!!
            this.itemType = itemType
            this.itemName = itemName
            this.teaSeasonConfigurationResponse = teaSeasonConfigurationResponse
            if (info != null) {
                this.mConfigurationItemInfo = info

                val list: MutableList<TeaSeasonQualityInfo> = ArrayList()
                for (i in teaSeasonConfigurationResponse?.Data!!.indices) {
                    var position = -1
                    for (j in info.tea_season_quality.indices) {
                        if (!StringHelper.isEmpty(info.tea_season_quality[j].id)
                            && teaSeasonConfigurationResponse.Data[i]._id == info.tea_season_quality[j].lu_tea_quality_id
                        ) {
                            position = j
                        }
                    }
                    if (position != -1) {
                        Log.e("test", i.toString() + " position:" + position)
                        info.tea_season_quality[position].check = true
                        list.add(info.tea_season_quality[position])
                    } else {
                        Log.e("test", i.toString() + " position:" + position)
                        val data = TeaSeasonQualityInfo()
                        data.check = false
                        data.lu_tea_quality_id = teaSeasonConfigurationResponse.Data[i]._id
                        data.name = teaSeasonConfigurationResponse.Data[i].name
                        list.add(data)
                    }
                }
                this.mConfigurationItemInfo!!.tea_season_quality = list

            } else {
                this.mConfigurationItemInfo = ConfigurationItemInfo()
                val list: MutableList<TeaSeasonQualityInfo> = ArrayList()
                for (i in teaSeasonConfigurationResponse?.Data!!) {
                    val data = TeaSeasonQualityInfo()
                    data.lu_tea_quality_id = i._id
                    data.name = i.name
                    list.add(data)
                }
                this.mConfigurationItemInfo!!.tea_season_quality = list
            }
            return AddTeaSeasonDialog(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sheetView: View = layoutInflater.inflate(R.layout.dialog_add_tea_season, null)
        setContentView(sheetView)
        binding = DataBindingUtil.bind(sheetView)!!
        binding.info = mConfigurationItemInfo

        if (!StringHelper.isEmpty(mConfigurationItemInfo?.id))
            binding.txtTitle.text = "Edit $itemName"
        else
            binding.txtTitle.text = "Add $itemName"

        binding.txtSave.setOnClickListener {
            adapter?.checkValidation()
            Timer().schedule(500) {
                MainScope().launch {
                    withContext(Dispatchers.Default) {

                    }
                    Log.e("test", "valid>>>>>:" + valid())
                    if (valid()) {
                        mListener?.onAddConfigurationItem(itemType, mConfigurationItemInfo!!)
                        dismiss()
                    }
                }
            }
        }

        setAdapter(mConfigurationItemInfo!!.tea_season_quality)

        binding.imgClose.setOnClickListener { dismiss() }

    }

    private fun valid(): Boolean {
        var valid = true

        if (adapter?.validate!!) {
            valid = true
            mConfigurationItemInfo!!.tea_season_quality = adapter?.list!!
        } else {
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(binding.edtName.text.toString().trim())) {
            binding.layoutName.error = null
            binding.layoutName.isErrorEnabled = false
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtName, binding.layoutName,
                mContext.getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        return valid
    }

    private fun setAdapter(list: MutableList<TeaSeasonQualityInfo>) {
        if (list.isNotEmpty()) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.recyclerView.setHasFixedSize(true)
            adapter = TeaSeasonQualityListAdapter(mContext, list)
            binding.recyclerView.adapter = adapter
            val linearLayoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.layoutManager = linearLayoutManager
        } else {
            binding.recyclerView.visibility = View.GONE
        }
    }


    override fun onStart() {
        super.onStart()
//        this.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
//        this.window!!.setBackgroundDrawableResource(android.R.color.transparent);
        this.window!!.setLayout(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }

    fun BottomSheetDialogFragment.setTransparentBackground() {
        dialog?.apply {
            setOnShowListener {
                val bottomSheet =
                    findViewById<View?>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet?.setBackgroundResource(android.R.color.transparent)
            }
        }
    }

}