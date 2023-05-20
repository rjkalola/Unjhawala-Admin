package com.unjhawalateaadmin.dashboard.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
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
import com.unjhawalateaadmin.MyApplication
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
import com.unjhawalateaadmin.dashboard.data.model.*
import com.unjhawalateaadmin.dashboard.ui.dialog.SelectItemBottomSheetDialog
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import com.unjhawalateaadmin.databinding.ActivityAddAvailableTeaSampleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.parceler.Parcels
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class AddAvailableTeaSampleTestingActivity : BaseActivity(), View.OnClickListener,
    SelectItemListener,
    OnDateSetListener, SelectAttachmentListener, EasyPermissions.PermissionCallbacks,
    PickiTCallbacks {
    private lateinit var binding: ActivityAddAvailableTeaSampleBinding
    private lateinit var mContext: Context
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private lateinit var selectItemBottomSheetDialog: SelectItemBottomSheetDialog
    private lateinit var teaSampleConfigurationResponse: AvailableTeaSampleConfigurationResponse
    lateinit var teaSampleInfo: TeaSampleInfo
    private var currentPhotoPath: String = ""
    private var imagePath: String = ""
    private var selectedImagePath: String = ""
    private var vendorId: String = ""
    private lateinit var imagePickerUtility: ImagePickerUtility;
    private lateinit var data: AvailableTeaSampleListResponse
    private var fileAction = 0;
    lateinit var fileUri: Uri

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor()
        mContext = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_available_tea_sample)
        mContext = this
        setStatusBarColor(resources.getColor(R.color.colorAccentLight))
        setupToolbar(getString(R.string.add_details), true)
        imagePickerUtility = ImagePickerUtility(this, this, this)

        getTeaSampleConfigurationResponseObservers()
        storeTeaSampleResponseObservers()

        binding.txtSave.setOnClickListener(this)
        binding.edtVendor.setOnClickListener(this)
        binding.edtDate.setOnClickListener(this)
        binding.routSelectAttachment.setOnClickListener(this)

        getIntentData()

    }

    private fun getIntentData() {
        if (intent.extras != null && intent.hasExtra(AppConstants.IntentKey.AVAILABLE_TEA_SAMPLE_DATA)) {
            data = Parcels.unwrap<AvailableTeaSampleListResponse?>(
                intent?.getParcelableExtra(AppConstants.IntentKey.AVAILABLE_TEA_SAMPLE_DATA)
            )
            showCustomProgressDialog(binding.progressBarView.routProgress)
            dashboardViewModel.getAvailableTeaSampleConfigurationResponse()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txtSave ->
                if (valid()) {
                    val gson: Gson = (mContext.applicationContext as MyApplication).provideGson()
                    showProgressDialog(mContext, "")
                    dashboardViewModel.storeTeaConfirmation(
                        vendorId,
                        binding.edtDate.text.toString().trim(),
                        selectedImagePath,
                        gson.toJson(data.Data)
                    )
                }
            R.id.edtVendor -> if (teaSampleConfigurationResponse.vendors.isNotEmpty()) {
                showSelectItemDialog(
                    teaSampleConfigurationResponse.vendors,
                    getString(R.string.select_vendor),
                    AppConstants.DialogIdentifier.SELECT_VENDOR
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
            R.id.routSelectAttachment -> {
                checkPermission()
            }
        }
    }

    private fun valid(): Boolean {
        var valid = true

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
        dashboardViewModel.mAvailableTeaSampleConfigurationResponse.observe(this) { response ->
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
        dashboardViewModel.storeAvailableTeaSample.observe(this) { response ->
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
                        moveActivity(mContext, DashBoardActivity::class.java, true, true, null)
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
            vendorId = teaSampleConfigurationResponse.vendors[position]._id
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
        selectedImagePath = file.absolutePath
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