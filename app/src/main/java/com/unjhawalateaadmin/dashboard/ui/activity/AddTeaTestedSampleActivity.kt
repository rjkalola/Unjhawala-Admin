package com.unjhawalateaadmin.dashboard.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.imateplus.imagepickers.models.FileWithPath
import com.imateplus.imagepickers.pickiT.PickiTCallbacks
import com.imateplus.imagepickers.utils.Constant
import com.imateplus.imagepickers.utils.FileUtils
import com.imateplus.utilities.callback.OnDateSetListener
import com.imateplus.utilities.fragments.DatePickerFragment
import com.imateplus.utilities.utils.AlertDialogHelper
import com.imateplus.utilities.utils.DateFormatsConstants
import com.imateplus.utilities.utils.StringHelper
import com.imateplus.utilities.utils.ValidationUtil
import com.unjhawalateaadmin.BuildConfig
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.callback.SelectAttachmentListener
import com.unjhawalateaadmin.common.callback.SelectItemListener
import com.unjhawalateaadmin.common.data.model.ModuleInfo
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.ui.fragment.SelectAttachmentDialog
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.common.utils.AppUtils.convertUriToFile
import com.unjhawalateaadmin.common.utils.ImagePickerUtility
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleConfigurationResponse
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleInfo
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleTestingInfo
import com.unjhawalateaadmin.dashboard.data.ui.adapter.TeaSampleRatingAdapter
import com.unjhawalateaadmin.dashboard.data.ui.adapter.TeaSamplesAdapter
import com.unjhawalateaadmin.dashboard.ui.dialog.SelectItemBottomSheetDialog
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.ActivityAddTeaSampleTestingBinding
import com.unjhawalateaadmin.databinding.ActivityAddTeaTestedSampleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.parceler.Parcels
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class AddTeaTestedSampleActivity : BaseActivity(), View.OnClickListener, SelectItemListener,
    OnDateSetListener, SelectAttachmentListener, EasyPermissions.PermissionCallbacks,
    PickiTCallbacks, TextWatcher {
    private lateinit var binding: ActivityAddTeaTestedSampleBinding
    private lateinit var mContext: Context
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private lateinit var selectItemBottomSheetDialog: SelectItemBottomSheetDialog
    private lateinit var teaSampleConfigurationResponse: TeaSampleConfigurationResponse
    lateinit var addTeaSampleTestingInfo: TeaSampleTestingInfo
    var levelFirstList: MutableList<ModuleInfo> = ArrayList()
    var levelSecondList: MutableList<ModuleInfo> = ArrayList()
    private var currentPhotoPath: String = ""
    private var imagePath: String = ""
    private lateinit var imagePickerUtility: ImagePickerUtility;
    private var fileAction = 0;
    lateinit var fileUri: Uri

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor()
        mContext = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_tea_tested_sample)
        mContext = this
        setStatusBarColor(resources.getColor(R.color.colorAccentLight))
        imagePickerUtility = ImagePickerUtility(this, this, this)
        getTeaSampleConfigurationResponseObservers()
        storeTeaSampleResponseObservers()
        testedSampleDetailsResponseObservers()

        binding.txtAdd.setOnClickListener(this)
        binding.edtPersonalGrade.setOnClickListener(this)
        binding.edtCutting.setOnClickListener(this)
        binding.edtColour.setOnClickListener(this)
        binding.edtDensity.setOnClickListener(this)
        binding.edtSourceLevel1.setOnClickListener(this)
        binding.edtSourceLevel2.setOnClickListener(this)
        binding.edtSourceLevel3.setOnClickListener(this)
        binding.edtSeasonAndQuality.setOnClickListener(this)
        binding.edtOurQuality.setOnClickListener(this)
        binding.edtTeaPreference.setOnClickListener(this)
        binding.edtMFGDate.setOnClickListener(this)
        binding.routSelectAttachment.setOnClickListener(this)
        binding.edtVendor.setOnClickListener(this)
        binding.edtGarden.setOnClickListener(this)
        binding.edtGrades.setOnClickListener(this)
        
        binding.edtBag.addTextChangedListener(this)
        binding.edtWeight.addTextChangedListener(this)

        getIntentData()
    }

    private fun getIntentData() {
        if (intent.extras != null && intent.hasExtra(AppConstants.IntentKey.TEA_SAMPLE_INFO)) {
            val addTeaSampleTestingInfo = Parcels.unwrap<TeaSampleTestingInfo?>(
                intent?.getParcelableExtra(AppConstants.IntentKey.TEA_SAMPLE_INFO)
            )
            setupToolbar(addTeaSampleTestingInfo.vendor_name, true)
            showCustomProgressDialog(binding.progressBarView.routProgress)
            dashboardViewModel.teaTestingDataDetails(addTeaSampleTestingInfo.id!!)
        }
    }

    private fun setRatingAdapter(rating: String) {
        binding.rvRatings.setHasFixedSize(true)
        val adapter = TeaSampleRatingAdapter(mContext, this)
        binding.rvRatings.adapter = adapter
        val layoutManager =
            GridLayoutManager(mContext, 5)
        adapter.setRating(rating)
        binding.rvRatings.layoutManager = layoutManager
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txtAdd -> {
                if (valid()) {
                    addTeaSampleTestingInfo.note = binding.edtNotes.text.toString().trim()
                    showProgressDialog(mContext, "")
                    dashboardViewModel.storeTeaTestedSampleResponse(addTeaSampleTestingInfo)
                }
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
            R.id.edtPersonalGrade -> if (teaSampleConfigurationResponse.grades.isNotEmpty()) {
                showSelectItemDialog(
                    teaSampleConfigurationResponse.grades,
                    getString(R.string.select_personal_grade),
                    AppConstants.DialogIdentifier.SELECT_PERSONAL_GRADE
                )
            }
            R.id.edtCutting -> if (teaSampleConfigurationResponse.cuttings.isNotEmpty()) {
                showSelectItemDialog(
                    teaSampleConfigurationResponse.cuttings,
                    getString(R.string.select_cutting),
                    AppConstants.DialogIdentifier.SELECT_CUTTING
                )
            }
            R.id.edtColour -> if (teaSampleConfigurationResponse.colours.isNotEmpty()) {
                showSelectItemDialog(
                    teaSampleConfigurationResponse.colours,
                    getString(R.string.select_colour),
                    AppConstants.DialogIdentifier.SELECT_COLOUR
                )
            }
            R.id.edtDensity -> if (teaSampleConfigurationResponse.densities.isNotEmpty()) {
                showSelectItemDialog(
                    teaSampleConfigurationResponse.densities,
                    getString(R.string.select_density),
                    AppConstants.DialogIdentifier.SELECT_DENSITY
                )
            }
            R.id.edtSourceLevel1 -> if (teaSampleConfigurationResponse.sources.isNotEmpty()) {
                showSelectItemDialog(
                    teaSampleConfigurationResponse.sources,
                    getString(R.string.select_tea_source_level_1),
                    AppConstants.DialogIdentifier.SELECT_TEA_SOURCE_LEVEL_1
                )
            }
            R.id.edtSourceLevel2 -> if (levelFirstList.isNotEmpty()) {
                showSelectItemDialog(
                    levelFirstList,
                    getString(R.string.select_tea_source_level_2),
                    AppConstants.DialogIdentifier.SELECT_TEA_SOURCE_LEVEL_2
                )
            }
            R.id.edtSourceLevel3 -> if (levelSecondList.isNotEmpty()) {
                showSelectItemDialog(
                    levelSecondList,
                    getString(R.string.select_tea_source_level_3),
                    AppConstants.DialogIdentifier.SELECT_TEA_SOURCE_LEVEL_3
                )
            }
            R.id.edtSeasonAndQuality -> if (teaSampleConfigurationResponse.seasons.isNotEmpty()) {
                showSelectItemDialog(
                    teaSampleConfigurationResponse.seasons,
                    getString(R.string.select_season_and_quality),
                    AppConstants.DialogIdentifier.SELECT_SEASON_AND_QUALITY
                )
            }
            R.id.edtOurQuality -> if (teaSampleConfigurationResponse.qualities.isNotEmpty()) {
                showSelectItemDialog(
                    teaSampleConfigurationResponse.qualities,
                    getString(R.string.select_our_quality),
                    AppConstants.DialogIdentifier.SELECT_OUR_QUALITY
                )
            }
            R.id.edtTeaPreference -> if (teaSampleConfigurationResponse.preferences.isNotEmpty()) {
                showSelectItemDialog(
                    teaSampleConfigurationResponse.preferences,
                    getString(R.string.select_tea_preference),
                    AppConstants.DialogIdentifier.SELECT_TEA_PREFERENCE
                )
            }
            R.id.edtMFGDate -> {
                if (!StringHelper.isEmpty(binding.edtMFGDate.text.toString())) {
                    val date = binding.edtMFGDate.text.toString()
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
            R.id.routSelectAttachment -> {
                checkPermission()
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        var totalAmount = 0
        if (!StringHelper.isEmpty(binding.edtBag.text.toString().trim())
            && !StringHelper.isEmpty(binding.edtWeight.text.toString().trim())
        ) {
            val bag = binding.edtBag.text.toString().trim().toInt()
            val weight = binding.edtWeight.text.toString().trim().toInt()
            totalAmount = bag * weight
            binding.edtTotalQuantity.setText(totalAmount.toString())
        } else {
            binding.edtTotalQuantity.setText("")
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

        if (!ValidationUtil.isEmptyEditText(binding.edtRate.text.toString().trim())) {
            binding.layoutRate.isErrorEnabled = false
            binding.edtRate.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtRate, binding.layoutRate,
                getString(R.string.empty_edittext_error)
            )
            valid = false
        }

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

        if (!ValidationUtil.isEmptyEditText(binding.edtPersonalGrade.text.toString().trim())) {
            binding.layoutPersonalGrade.isErrorEnabled = false
            binding.edtPersonalGrade.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtPersonalGrade, binding.layoutPersonalGrade,
                getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(binding.edtCutting.text.toString().trim())) {
            binding.layoutCutting.isErrorEnabled = false
            binding.edtCutting.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtCutting, binding.layoutCutting,
                getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(binding.edtColour.text.toString().trim())) {
            binding.layoutColour.isErrorEnabled = false
            binding.edtColour.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtColour, binding.layoutColour,
                getString(R.string.empty_edittext_error)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(binding.edtDensity.text.toString().trim())) {
            binding.layoutDensity.isErrorEnabled = false
            binding.edtDensity.error = null
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtDensity, binding.layoutDensity,
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
        dashboardViewModel.storeTeaSampleTestingResponse.observe(this) { response ->
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
                        AppUtils.showMessage(mContext, response.Message!!)
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun testedSampleDetailsResponseObservers() {
        dashboardViewModel.mTeaTestedSampleDetailsResponse.observe(this) { response ->
            hideCustomProgressDialog(binding.progressBarView.routProgress)
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
                        addTeaSampleTestingInfo = TeaSampleTestingInfo()
                        addTeaSampleTestingInfo = response.Data
                        binding.info = addTeaSampleTestingInfo
                        if (!StringHelper.isEmpty(response.Data.attachment)) {
                            binding.txtChooseAttachment.visibility = View.GONE
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
                        }
                        setRatingAdapter(response.Data.rating!!)
                        dashboardViewModel.getTeaSampleConfigurationResponse()
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
            addTeaSampleTestingInfo.vendor_id =
                teaSampleConfigurationResponse.vendors[position]._id
        } else if (action == AppConstants.DialogIdentifier.SELECT_GARDEN) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtGarden.setText(teaSampleConfigurationResponse.gardens[position].name)
            addTeaSampleTestingInfo.lu_garden_id =
                teaSampleConfigurationResponse.gardens[position]._id
        } else if (action == AppConstants.DialogIdentifier.SELECT_GRADE) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtGrades.setText(teaSampleConfigurationResponse.grades[position].name)
            addTeaSampleTestingInfo.lu_tea_grade_id =
                teaSampleConfigurationResponse.grades[position]._id
        } else if (action == AppConstants.DialogIdentifier.SELECT_PERSONAL_GRADE) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtPersonalGrade.setText(teaSampleConfigurationResponse.grades[position].name)
            addTeaSampleTestingInfo.lu_tea_personal_grade_id =
                teaSampleConfigurationResponse.grades[position]._id
        } else if (action == AppConstants.DialogIdentifier.SELECT_CUTTING) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtCutting.setText(teaSampleConfigurationResponse.cuttings[position].name)
            addTeaSampleTestingInfo.lu_tea_cutting_id =
                teaSampleConfigurationResponse.cuttings[position]._id
        } else if (action == AppConstants.DialogIdentifier.SELECT_COLOUR) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtColour.setText(teaSampleConfigurationResponse.colours[position].name)
            addTeaSampleTestingInfo.lu_tea_colour_id =
                teaSampleConfigurationResponse.colours[position]._id
        } else if (action == AppConstants.DialogIdentifier.SELECT_DENSITY) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtDensity.setText(teaSampleConfigurationResponse.densities[position].name)
            addTeaSampleTestingInfo.lu_tea_density_id =
                teaSampleConfigurationResponse.densities[position]._id
        } else if (action == AppConstants.DialogIdentifier.SELECT_TEA_SOURCE_LEVEL_1) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtSourceLevel1.setText(teaSampleConfigurationResponse.sources[position].name)
            addTeaSampleTestingInfo.lu_tea_source_level_1_id =
                teaSampleConfigurationResponse.sources[position]._id

            levelFirstList.clear()
            levelSecondList.clear()

            addTeaSampleTestingInfo.lu_tea_source_level_2_id = ""
            addTeaSampleTestingInfo.lu_tea_source_level_3_id = ""

            binding.edtSourceLevel2.setText("")
            binding.edtSourceLevel3.setText("")

            binding.layoutSourceLevel2.visibility = View.GONE
            binding.layoutSourceLevel3.visibility = View.GONE

            if (teaSampleConfigurationResponse.sources[position].first_levels.isNotEmpty()) {
                binding.layoutSourceLevel2.visibility = View.VISIBLE
                levelFirstList.addAll(teaSampleConfigurationResponse.sources[position].first_levels)
            }

        } else if (action == AppConstants.DialogIdentifier.SELECT_TEA_SOURCE_LEVEL_2) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtSourceLevel2.setText(levelFirstList[position].name)
            addTeaSampleTestingInfo.lu_tea_source_level_2_id = levelFirstList[position]._id

            levelSecondList.clear()
            addTeaSampleTestingInfo.lu_tea_source_level_3_id = ""
            binding.edtSourceLevel3.setText("")

            binding.layoutSourceLevel3.visibility = View.GONE

            if (levelFirstList[position].second_levels.isNotEmpty()) {
                binding.layoutSourceLevel3.visibility = View.VISIBLE
                levelSecondList.addAll(levelFirstList[position].second_levels)
            }
        } else if (action == AppConstants.DialogIdentifier.SELECT_TEA_SOURCE_LEVEL_3) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtSourceLevel3.setText(levelSecondList[position].name)
            addTeaSampleTestingInfo.lu_tea_source_level_3_id = levelSecondList[position]._id
        } else if (action == AppConstants.DialogIdentifier.SELECT_SEASON_AND_QUALITY) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtSeasonAndQuality.setText(teaSampleConfigurationResponse.seasons[position].name)
            addTeaSampleTestingInfo.lu_tea_season_id =
                teaSampleConfigurationResponse.seasons[position]._id
        } else if (action == AppConstants.DialogIdentifier.SELECT_OUR_QUALITY) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtOurQuality.setText(teaSampleConfigurationResponse.qualities[position].name)
            addTeaSampleTestingInfo.quality_id =
                teaSampleConfigurationResponse.qualities[position]._id
        } else if (action == AppConstants.DialogIdentifier.SELECT_TEA_PREFERENCE) {
            selectItemBottomSheetDialog.dismiss()
            binding.edtTeaPreference.setText(teaSampleConfigurationResponse.preferences[position].name)
            addTeaSampleTestingInfo.lu_tea_product_preference_id =
                teaSampleConfigurationResponse.preferences[position]._id
        } else if (action == AppConstants.DialogIdentifier.TEA_SAMPLE_RATING) {
            addTeaSampleTestingInfo.rating =
                (position + 1).toString()
            Log.e("test", "addTeaSampleTestingInfo.rating:" + addTeaSampleTestingInfo.rating)
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
            binding.edtMFGDate.setText(dateFormat.format(date.time))
            addTeaSampleTestingInfo.manufacturer_date = dateFormat.format(date.time)
        }
    }

    private fun checkPermission() {
        if (hasPermission()) {
            showSelectAttachmentDialog()
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.msg_storage_permission),
                AppConstants.IntentKey.EXTERNAL_STORAGE_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        }
    }

    private fun hasPermission(): Boolean {
        return EasyPermissions.hasPermissions(
            this, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
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
        showSelectAttachmentDialog()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    private fun showSelectAttachmentDialog() {
        val fm = (mContext as BaseActivity).supportFragmentManager
        val selectAttachmentDialog =
            SelectAttachmentDialog(this, this, 0, true, true, true)
        selectAttachmentDialog.show(fm, "selectAttachmentDialog")
    }

    override fun onSelectAttachment(action: Int) {
        when (action) {
            AppConstants.Type.SELECT_PHOTOS -> {
                fileAction = AppConstants.Action.SELECT_GALLERY_IMAGE
                onSelectFromGallery()
            }
            AppConstants.Type.SELECT_FROM_CAMERA -> {
                fileAction = AppConstants.Action.SELECT_CAMERA_IMAGE
                onSelectFromCamera()
            }
            AppConstants.Type.SELECT_PDF -> {
                fileAction = AppConstants.Action.SELECT_PDF
                onSelectPdf()
            }
        }
    }

    private fun onSelectFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        selectPhotosRequest.launch(intent)
    }

    private fun onSelectPdf() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        selectPdfRequest.launch(intent)
    }

    private fun onSelectFromCamera() {
        try {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var fileWithPath: FileWithPath = AppUtils.createImageFile(
                "",
                AppConstants.Type.CAMERA,
                AppConstants.FileExtension.JPG
            )
            currentPhotoPath = fileWithPath.filePath
            val photoURI = FileProvider.getUriForFile(
                mContext, BuildConfig.APPLICATION_ID + ".provider",
                fileWithPath.file
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            selectFromCameraRequest.launch(takePictureIntent)
        } catch (e: Exception) {

        }
    }

    var selectPhotosRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent = result.data!!
                if (data.data != null) {
                    fileUri = result?.data?.data!!
                    imagePickerUtility.getPath(data.data!!, Build.VERSION.SDK_INT)
                }
            }
        }

    var selectFromCameraRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val file = File(
                        FileUtils.getPath(mContext, Uri.parse(currentPhotoPath))!!
                    )
                    var realPath = ""
                    val fileWithPath: FileWithPath =
                        AppUtils.compressImage(file.absolutePath, File(file.absolutePath))!!

                    if (fileWithPath.uri != null)
                        realPath = FileUtils.getPath(
                            mContext,
                            Uri.parse(fileWithPath.filePath)
                        )!! else
                        file.path
                    Log.e("test", "realPath:$realPath")
                    imagePath = realPath
                    setUserImageFromFile(File(realPath));
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }

    var selectPdfRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                fileUri = result?.data?.data!!
                val data: Intent = result.data!!
                if (data.data != null) {
                    imagePickerUtility.getPath(data.data!!, Build.VERSION.SDK_INT)
                }
            }
        }

    private fun setUserImageFromFile(file: File) {
        addTeaSampleTestingInfo.file = file.absolutePath
        binding.txtChooseAttachment.visibility = View.GONE
        if (file.absolutePath.endsWith(".pdf")) {
            binding.imgPdf.visibility = View.VISIBLE
            binding.imgJpg.visibility = View.GONE
        } else {
            binding.imgPdf.visibility = View.GONE
            binding.imgJpg.visibility = View.VISIBLE
            AppUtils.setImageFromFile(
                mContext,
                file.absolutePath,
                binding.imgJpg,
                Constant.ImageScaleType.CENTER_CROP
            )

        }
//        binding.edtAttachment.setText(file.name)
    }

    override fun PickiTonUriReturned() {

    }

    override fun PickiTonStartListener() {

    }

    override fun PickiTonProgressUpdate(progress: Int) {

    }

    override fun PickiTonCompleteListener(
        path: String?,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        try {
            val realPath: String = path!!
            if (!StringHelper.isEmpty(realPath)) {
                imagePath = realPath
                setUserImageFromFile(File(realPath));
            } else {
                if (fileAction == AppConstants.Action.SELECT_PDF) {
                    val fileName = "PDF_" + SimpleDateFormat(
                        "yyyyMMdd_HHmmss",
                        Locale.US
                    ).format(Date()) + ".pdf"
                    val destinationFilePath = "$filesDir/$fileName"
                    val file = fileUri.convertUriToFile(this, destinationFilePath)
                    imagePath = destinationFilePath
                    setUserImageFromFile(file);
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun PickiTonMultipleCompleteListener(
        paths: ArrayList<String>,
        wasSuccessful: Boolean,
        Reason: String?
    ) {

    }

}