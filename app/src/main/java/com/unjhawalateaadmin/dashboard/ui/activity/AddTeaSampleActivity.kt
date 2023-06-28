package com.unjhawalateaadmin.dashboard.ui.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.DatePicker
import android.widget.ScrollView
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.imateplus.utilities.callback.OnDateSetListener
import com.imateplus.utilities.fragments.DatePickerFragment
import com.imateplus.utilities.utils.AlertDialogHelper
import com.imateplus.utilities.utils.DateFormatsConstants
import com.imateplus.utilities.utils.StringHelper
import com.imateplus.utilities.utils.ValidationUtil
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleConfigurationResponse
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleInfo
import com.unjhawalateaadmin.dashboard.ui.dialog.SelectItemBottomSheetDialog
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.ActivityAddTeaSampleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.parceler.Parcels
import java.text.SimpleDateFormat
import java.util.*


class AddTeaSampleActivity : BaseActivity(), View.OnClickListener, SelectItemListener,
    OnDateSetListener, TextWatcher {
    private lateinit var binding: ActivityAddTeaSampleBinding
    private lateinit var mContext: Context
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private lateinit var selectItemBottomSheetDialog: SelectItemBottomSheetDialog
    private lateinit var teaSampleConfigurationResponse: TeaSampleConfigurationResponse
    lateinit var addTeaSampleInfo: TeaSampleInfo
    var isSaveAndAdd: Boolean = false
    var isUpdate: Boolean = false
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor()
        mContext = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_tea_sample)
        mContext = this
        setStatusBarColor(resources.getColor(R.color.colorAccentLight))

        getTeaSampleConfigurationResponseObservers()
        storeTeaSampleResponseObservers()

        binding.txtAdd.setOnClickListener(this)
        binding.txtSaveAndAdd.setOnClickListener(this)
        binding.edtVendor.setOnClickListener(this)
        binding.edtGarden.setOnClickListener(this)
        binding.edtGrades.setOnClickListener(this)

        binding.edtBag.addTextChangedListener(this)
        binding.edtWeight.addTextChangedListener(this)
        binding.edtTotalQuantity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.edtTotalQuantity.isFocused){
                    var totalWeight = 0f
                    if (!StringHelper.isEmpty(binding.edtBag.text.toString().trim())
                        && !StringHelper.isEmpty(binding.edtTotalQuantity.text.toString().trim())
                    ) {
                        val bag = binding.edtBag.text.toString().trim().toInt()
                        val totalQuantity = binding.edtTotalQuantity.text.toString().trim().toInt()
                        totalWeight = totalQuantity.toFloat() / bag.toFloat()
                        binding.edtWeight.setText(totalWeight.toString())
                    } else {
                        binding.edtWeight.setText("")
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        /*  binding.edtRate.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
              if (actionId == EditorInfo.IME_ACTION_DONE) {
                  binding.scrollView.post(Runnable { binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN) })
                  true
              }
              else
                  false
          })*/

        getIntentData()

    }

    private fun getIntentData() {
        if (intent.extras != null && intent.hasExtra(AppConstants.IntentKey.TEA_SAMPLE_INFO)) {
            setupToolbar(getString(R.string.update_tea_sample), true)
            binding.btnSaveAndAdd.visibility = View.GONE
            addTeaSampleInfo = Parcels.unwrap<TeaSampleInfo?>(
                intent?.getParcelableExtra(AppConstants.IntentKey.TEA_SAMPLE_INFO)
            )
            binding.info = addTeaSampleInfo
        } else {
            setupToolbar(getString(R.string.create_tea_sample), true)
            binding.btnSaveAndAdd.visibility = View.VISIBLE
            addTeaSampleInfo = TeaSampleInfo()
            val date = Calendar.getInstance()
            val dateFormat = SimpleDateFormat(DateFormatsConstants.YYYY_MM_DD_DASH, Locale.US)
            binding.edtDate.setText(dateFormat.format(date.time))
            addTeaSampleInfo.created_date = dateFormat.format(date.time)
            binding.info = addTeaSampleInfo
        }
        showCustomProgressDialog(binding.progressBarView.routProgress)
        dashboardViewModel.getTeaSampleConfigurationResponse()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txtAdd, R.id.txtSaveAndAdd ->
                if (valid()) {
                    isSaveAndAdd = v.id == R.id.txtSaveAndAdd
                    showProgressDialog(mContext, "")
                    dashboardViewModel.storeTeaSampleResponse(addTeaSampleInfo)
                }

            R.id.edtVendor -> if (teaSampleConfigurationResponse.vendors.isNotEmpty()) {
                showSelectItemDialog(
                    teaSampleConfigurationResponse.vendors,
                    getString(R.string.select_vendor),
                    AppConstants.DialogIdentifier.SELECT_VENDOR
                )
            }

            R.id.edtGarden -> if (teaSampleConfigurationResponse.gardens.isNotEmpty()) {
                showSelectItemDialog(
                    teaSampleConfigurationResponse.gardens,
                    getString(R.string.select_garden),
                    AppConstants.DialogIdentifier.SELECT_GARDEN
                )
            }

            R.id.edtGrades -> if (teaSampleConfigurationResponse.grades.isNotEmpty()) {
                showSelectItemDialog(
                    teaSampleConfigurationResponse.grades,
                    getString(R.string.select_grade),
                    AppConstants.DialogIdentifier.SELECT_GRADE
                )
            }

            R.id.edtDate -> {
                if (!StringHelper.isEmpty(binding.edtDate.text.toString())) {
                    val date = binding.edtDate.text.toString()
                    showDatePicker(
                        0,
                        0,
                        AppConstants.DialogIdentifier.SELECT_DATE,
                        date
                    )
                } else {
                    showDatePicker(
                        0,
                        0,
                        AppConstants.DialogIdentifier.SELECT_DATE,
                        null
                    )
                }
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if(binding.edtBag.isFocused || binding.edtWeight.isFocused){
            var totalAmount = 0f
            if (!StringHelper.isEmpty(binding.edtBag.text.toString().trim())
                && !StringHelper.isEmpty(binding.edtWeight.text.toString().trim())
            ) {
                val bag = binding.edtBag.text.toString().trim().toInt()
                val weight = binding.edtWeight.text.toString().trim().toFloat()
                totalAmount = bag * weight
                binding.edtTotalQuantity.setText(totalAmount.toInt().toString())
            } else {
                binding.edtTotalQuantity.setText("")
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }

    private fun valid(): Boolean {
        var valid = true

        if (!ValidationUtil.isEmptyEditText(binding.edtTotalQuantity.text.toString().trim())) {
            binding.layoutTotalQuantity.isErrorEnabled = false
            binding.edtTotalQuantity.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtTotalQuantity, binding.layoutTotalQuantity,
                getString(R.string.empty_edittext_error)
            )
            valid = false
        }

//        if (!ValidationUtil.isEmptyEditText(binding.edtRate.text.toString().trim())) {
//            binding.layoutRate.isErrorEnabled = false
//            binding.edtRate.error = null
//        } else {
//            ValidationUtil.setErrorIntoInputTextLayout(
//                binding.edtRate, binding.layoutRate,
//                getString(R.string.empty_edittext_error)
//            )
//            valid = false
//        }

        if (!ValidationUtil.isEmptyEditText(binding.edtWeight.text.toString().trim())) {
            binding.layoutWeight.isErrorEnabled = false
            binding.edtWeight.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtWeight, binding.layoutWeight,
                getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(binding.edtBag.text.toString().trim())) {
            binding.layoutBag.isErrorEnabled = false
            binding.edtBag.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtBag, binding.layoutBag,
                getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(binding.edtGrades.text.toString().trim())) {
            binding.layoutGrades.isErrorEnabled = false
            binding.edtGrades.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtGrades, binding.layoutGrades,
                getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(binding.edtInvoiceNumber.text.toString().trim())) {
            binding.layoutInvoiceNumber.isErrorEnabled = false
            binding.edtInvoiceNumber.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtInvoiceNumber, binding.layoutInvoiceNumber,
                getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(binding.edtGarden.text.toString().trim())) {
            binding.layoutGarden.isErrorEnabled = false
            binding.edtGarden.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtGarden, binding.layoutGarden,
                getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(binding.edtVendor.text.toString().trim())) {
            binding.layoutVendor.isErrorEnabled = false
            binding.edtVendor.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtVendor, binding.layoutVendor,
                getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(binding.edtDate.text.toString().trim())) {
            binding.layoutDate.isErrorEnabled = false
            binding.edtDate.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtDate, binding.layoutDate,
                getString(R.string.empty_edittext_error)
            )
            valid = false
        }
        return valid
    }

    private fun getTeaSampleConfigurationResponseObservers() {
        dashboardViewModel.teaSampleConfigurationResponse.observe(this) { response ->
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
                        teaSampleConfigurationResponse = response

                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun storeTeaSampleResponseObservers() {
        dashboardViewModel.storeTeaSampleResponse.observe(this) { response ->
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
                        if (!isSaveAndAdd) {
                            setResult(Activity.RESULT_OK)
                            finish()
                        } else {
                            isUpdate = true
                            isSaveAndAdd = false
                            addTeaSampleInfo = TeaSampleInfo()
                            val date = Calendar.getInstance()
                            val dateFormat =
                                SimpleDateFormat(DateFormatsConstants.YYYY_MM_DD_DASH, Locale.US)
                            binding.edtDate.setText(dateFormat.format(date.time))
                            addTeaSampleInfo.created_date = dateFormat.format(date.time)
                            binding.info = addTeaSampleInfo
                        }
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun showSelectItemDialog(
        list: MutableList<ModuleInfo>,
        title: String? = "",
        identifyId: Int? = 0,
    ) {
        selectItemBottomSheetDialog =
            SelectItemBottomSheetDialog.newInstance(mContext, list, title, identifyId, this)
        selectItemBottomSheetDialog.show()
    }

    override fun onSelectItem(position: Int, action: Int, productType: Int) {
        if (action == AppConstants.DialogIdentifier.SELECT_VENDOR) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtVendor.setText(teaSampleConfigurationResponse.vendors[position].name)
            addTeaSampleInfo.vendor_id = teaSampleConfigurationResponse.vendors[position]._id
        } else if (action == AppConstants.DialogIdentifier.SELECT_GARDEN) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtGarden.setText(teaSampleConfigurationResponse.gardens[position].name)
            addTeaSampleInfo.lu_garden_id = teaSampleConfigurationResponse.gardens[position]._id
        } else if (action == AppConstants.DialogIdentifier.SELECT_GRADE) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtGrades.setText(teaSampleConfigurationResponse.grades[position].name)
            addTeaSampleInfo.lu_tea_grade_id = teaSampleConfigurationResponse.grades[position]._id
        }
    }

    private fun showDatePicker(minDate: Long, maxDate: Long, tag: String, selDate: String?) {
        val newFragment: DialogFragment = DatePickerFragment.newInstance(
            minDate,
            maxDate,
            selDate,
            DateFormatsConstants.YYYY_MM_DD_DASH,
            tag
        )
        newFragment.show(supportFragmentManager, tag)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        if (view!!.tag.toString() == AppConstants.DialogIdentifier.SELECT_DATE) {
            val date = Calendar.getInstance()
            date[year, month] = day
            val dateFormat = SimpleDateFormat(DateFormatsConstants.YYYY_MM_DD_DASH, Locale.US)
            binding.edtDate.setText(dateFormat.format(date.time))
            addTeaSampleInfo.created_date = dateFormat.format(date.time)
        }
    }

}