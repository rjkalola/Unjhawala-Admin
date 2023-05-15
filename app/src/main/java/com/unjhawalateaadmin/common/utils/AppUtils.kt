package com.unjhawalateaadmin.common.utils

import android.app.Activity
import android.content.*
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.provider.Settings.Secure
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.core.util.Pair
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.*
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.gson.Gson
import com.imateplus.imagepickers.models.FileWithPath
import com.imateplus.imagepickers.utils.Constant
import com.imateplus.imagepickers.utils.GlideUtil
import com.imateplus.imagepickers.utils.ImageUtils
import com.unjhawalateaadmin.MyApplication
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.authentication.data.model.User
import com.unjhawalateaadmin.authentication.data.model.Users
import com.unjhawalateaadmin.authentication.ui.activity.LoginActivity
import com.unjhawalateaadmin.common.callback.SelectDateRangeListener
import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.data.model.FcmData
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.ui.activity.WebViewActivity
import com.imateplus.utilities.callback.DialogButtonClickListener
import com.imateplus.utilities.utils.AlertDialogHelper
import com.imateplus.utilities.utils.DateFormatsConstants
import com.imateplus.utilities.utils.DateHelper
import com.imateplus.utilities.utils.StringHelper
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object AppUtils {
    fun getUserPreference(context: Context?): User? {
        if (context != null && context.applicationContext != null) {
            val gson: Gson = (context.applicationContext as MyApplication).provideGson()
            val userInfo: String =
                MyApplication().preferenceGetString(AppConstants.SharedPrefKey.USER_INFO, "")
            if (!StringHelper.isEmpty(userInfo)) {
                return gson.fromJson(userInfo, User::class.java)
            }
        }
        return null
    }

    fun setUserPreference(context: Context, userInfo: User?) {
        if (context.applicationContext != null) {
            val gson: Gson =
                (context.applicationContext as MyApplication).provideGson()
            MyApplication().preferencePutString(
                AppConstants.SharedPrefKey.USER_INFO,
                gson.toJson(userInfo)
            )
        }
    }

    fun getDeviceId(context: Context?): String? {
        if (context != null && context.applicationContext != null) {
            val deviceId: String =
                MyApplication().preferenceGetString(AppConstants.SharedPrefKey.DEVICE_ID, "")
            if (!StringHelper.isEmpty(deviceId)) {
                return deviceId
            }
        }
        return null
    }


    fun setDeviceId(context: Context, deviceId: String?) {
        if (context.applicationContext != null) {
            MyApplication().preferencePutString(
                AppConstants.SharedPrefKey.DEVICE_ID,
                deviceId!!
            )
        }
    }

    fun getLoginUsers(context: Context?): Users? {
        if (context != null && context.applicationContext != null) {
            val gson: Gson = (context.applicationContext as MyApplication).provideGson()
            val users: String =
                MyApplication().preferenceGetString(AppConstants.SharedPrefKey.USERS, "")
            if (!StringHelper.isEmpty(users)) {
                return gson.fromJson(users, Users::class.java)
            }
        }
        return null
    }

    fun setLoginUsers(context: Context?, user: User) {
        if (context != null
            && context.applicationContext != null
        ) {
            var users: Users? = null
            if (getLoginUsers(context) != null)
                users = getLoginUsers(context)
            else users = Users()
            if (users?.users != null && users.users.size > 0) {
                Log.e("test", "users size:" + users.users.size);
                for (i in users.users.indices) {
                    Log.e("test", "users i:$i");
                    val userInfo: User = users.users[i]
                    if (isSameEmail(
                            if (!StringHelper.isEmpty(userInfo.email)) userInfo.email else "",
                            if (!StringHelper.isEmpty(user.email)) user.email else ""
                        )
                    ) {
                        users.users.removeAt(i)
                        break
                    }
                }
            }
            if (users?.users != null && users.users.size > 0) users.users
                .add(0, user) else {
                users?.users!!.add(user)
            }
            val gson: Gson = (context.applicationContext as MyApplication).provideGson()
            MyApplication().preferencePutString(
                AppConstants.SharedPrefKey.USERS,
                gson.toJson(users)
            )
        }
    }

    fun setLoginUsers(context: Context?, users: Users?) {
        if (context != null
            && context.applicationContext != null
        ) {
            val gson: Gson = (context.applicationContext as MyApplication).provideGson()
            MyApplication().preferencePutString(
                AppConstants.SharedPrefKey.USERS,
                gson.toJson(users)
            )
        }
    }

//    fun getChatUserPreference(context: Context?): ChatUsersListResponse? {
//        if (context != null && context.applicationContext != null) {
//            val gson: Gson = (context.applicationContext as MyApplication).provideGson()
//            val userInfo: String =
//                MyApplication().preferenceGetString(AppConstants.SharedPrefKey.CHAT_USER_INFO, "")
//            if (!StringHelper.isEmpty(userInfo)) {
//                return gson.fromJson(userInfo, ChatUsersListResponse::class.java)
//            }
//        }
//        return null
//    }
//
//
//    fun setChatUserPreference(context: Context, chatUsersListResponse: ChatUsersListResponse?) {
//        if (context.applicationContext != null) {
//            val gson: Gson =
//                (context.applicationContext as MyApplication).provideGson()
//            MyApplication().preferencePutString(
//                AppConstants.SharedPrefKey.CHAT_USER_INFO,
//                gson.toJson(chatUsersListResponse)
//            )
//        }
//    }

    fun isSameEmail(email1: String, email2: String): Boolean {
        val address = Pattern.compile("([\\w\\.]+)@([\\w\\.]+\\.\\w+)")
        val match1 = address.matcher(email1)
        val match2 = address.matcher(email2)
        if (!match1.find() || !match2.find()) return false //Not an e-mail address? Already false
        return if (!match1.group(2)
                .equals(match2.group(2), ignoreCase = true)
        ) false else when (match1.group(2).toLowerCase()) {
            "gmail.com" -> {
                val gmail1 = match1.group(1).replace(".", "")
                val gmail2 = match2.group(1).replace(".", "")
                gmail1.equals(gmail2, ignoreCase = true)
            }
            else -> match1.group(1).equals(match2.group(1), ignoreCase = true)
        } //Not same serve? Already false
    }

    fun handleUnauthorized(context: Context?, baseResponse: BaseResponse?) {
        if (context == null || baseResponse == null) return
        if (baseResponse.ErrorCode == AppConstants.UNAUTHORIZED) {
            AlertDialogHelper.showDialog(
                context,
                null,
                context.getString(R.string.msg_unauthorized),
                context.getString(R.string.ok),
                null,
                false, object : DialogButtonClickListener {
                    override fun onPositiveButtonClicked(dialogIdentifier: Int) {
                        if (context is BaseActivity) {
                            MyApplication().clearData()
                            context.moveActivity(
                                context,
                                LoginActivity::class.java, true, true, null
                            )
                        }
                    }

                    override fun onNegativeButtonClicked(dialogIdentifier: Int) {

                    }
                },
                0
            )
        } else {
            handleResponseMessage(context, baseResponse.Message!!)
        }
    }

    private fun handleResponseMessage(context: Context, messageString: String) {
        try {
            if (context == null) return
            var message: String? = ""
            if (messageString.contains("ERR") || messageString.contains("SUCC")) {
                message = getStringResourceByName(context, messageString)
            } else {
                message = messageString
            }
            AlertDialogHelper.showDialogWithHtml(
                context, null, message, context.getString(R.string.ok),
                null, false, null, 0
            )
        } catch (e: Exception) {

        }
    }

    private fun getStringResourceByName(mContext: Context?, aString: String?): String {
        try {
            if (mContext == null) return ""
            return if (!StringHelper.isEmpty(aString)) {
                val packageName = mContext.packageName
                var message = mContext.getString(
                    mContext.resources.getIdentifier(
                        aString,
                        "string",
                        packageName
                    )
                )
                if (StringHelper.isEmpty(message)) {
                    message = mContext.getString(R.string.error_unknown)
                }
                message
            } else {
                mContext.getString(R.string.error_unknown)
            }
        } catch (e: Exception) {

        }
        return ""
    }

    fun getHttpErrorMessage(context: Context, statusCode: Int): String? {
        var errorMessage = ""
        when (statusCode) {
            400 -> errorMessage = context.getString(R.string.error_bad_request_400)
            401 -> errorMessage = context.getString(R.string.error_unauthorized_401)
            403 -> errorMessage = context.getString(R.string.error_forbidden_403)
            404 -> errorMessage = context.getString(R.string.error_not_found_404)
            405 -> errorMessage = context.getString(R.string.error_method_not_allowed_405)
            408 -> errorMessage = context.getString(R.string.error_request_timeout_408)
            413 -> errorMessage = context.getString(R.string.error_request_entity_too_large_413)
            414 -> {
                errorMessage = context.getString(R.string.error_request_uri_too_long_414)
                errorMessage = context.getString(R.string.error_request_uri_too_long_414)
            }
            500 -> errorMessage = context.getString(R.string.error_internal_server_error_500)
            else -> errorMessage = context.getString(R.string.error_unknown)
        }
        return errorMessage
    }

    fun getEmptyUserDrawable(mContext: Context): Drawable? {
        return ResourcesCompat.getDrawable(mContext.resources, R.drawable.ic_empty_user, null)
    }

    fun getEmptyGalleryDrawable(mContext: Context): Drawable? {
        return ResourcesCompat.getDrawable(
            mContext.resources,
            R.drawable.ic_empty_image_placeholder,
            null
        )
    }

    fun setUserImage(mContext: Context, image: String?, imageUser: AppCompatImageView) {
        if (!StringHelper.isEmpty(image)) {
            GlideUtil.loadImageUsingGlideTransformation(
                image,
                imageUser,
                Constant.TransformationType.CIRCLECROP_TRANSFORM,
                AppUtils.getEmptyUserDrawable(mContext),
                AppUtils.getEmptyUserDrawable(mContext),
                Constant.ImageScaleType.CENTER_CROP,
                0,
                0,
                "",
                0,
                null
            )
        } else {
            imageUser.setImageDrawable(
                getEmptyUserDrawable(mContext)
            )
        }
    }

    fun setImage(mContext: Context, image: String?, imageView: AppCompatImageView, scaleType: Int) {
        if (!StringHelper.isEmpty(image)) {
            GlideUtil.loadImage(
                image,
                imageView,
                getEmptyGalleryDrawable(mContext),
                getEmptyGalleryDrawable(mContext),
                scaleType,
                null
            )
        } else {
            imageView.setImageDrawable(
                getEmptyGalleryDrawable(mContext)
            )
        }
    }

    fun setImageFromFile(
        mContext: Context,
        image: String?,
        imageView: AppCompatImageView,
        scaleType: Int
    ) {
        if (!StringHelper.isEmpty(image)) {
            GlideUtil.loadImageFromFile(
                imageView,
                File(image!!),
                AppUtils.getEmptyGalleryDrawable(mContext),
                AppUtils.getEmptyGalleryDrawable(mContext),
                scaleType,
                null
            )
        } else {
            imageView.setImageDrawable(
                getEmptyGalleryDrawable(mContext)
            )
        }
    }

    fun getApiDateFormat(input: String?): String {
        return if (!StringHelper.isEmpty(input)) {
            DateHelper.changeDateFormat(
                input,
                AppConstants.defaultDateFormat,
                AppConstants.apiDateFormat
            )
        } else {
            ""
        }
    }

    fun getDefaultDateFormat(input: String?): String {
        return if (!StringHelper.isEmpty(input)) {
            DateHelper.changeDateFormat(
                input,
                AppConstants.apiDateFormat,
                AppConstants.defaultDateFormat
            )
        } else {
            ""
        }
    }

    fun getDeviceToken(): String {
        val deviceToken = Secure.getString(
            MyApplication().getContext().contentResolver,
            Secure.ANDROID_ID
        )
        return if (!StringHelper.isEmpty(deviceToken)) {
            deviceToken
        } else {
            ""
        }
    }

    fun getDeviceModel(): String {
        val model = Build.MODEL // returns model name
        return if (!StringHelper.isEmpty(model)) {
            model
        } else {
            ""
        }
    }

    @Throws(IOException::class)
    fun createImageFile(title: String, type: String, imageExtension: String): FileWithPath {
        var imageFileName = ""
        var storageDir: File? = null
        val fileWithPath = FileWithPath()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        if (imageExtension == AppConstants.FileExtension.PDF) {
            imageFileName = "DOCUMENT_" + timeStamp + "_"
            storageDir = File(Environment.getExternalStorageDirectory(), AppConstants.Type.PDF)
        } else {
            if (type == AppConstants.Type.CAMERA) {
                imageFileName = "IMAGE_" + timeStamp + "_"
                storageDir = File(
                    Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM
                    ), "Camera"
                )
            } else {
                imageFileName = "IMAGE_" + timeStamp + "_"
                storageDir =
                    File(Environment.getExternalStorageDirectory(), AppConstants.Directory.IMAGES)
            }
        }
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            imageExtension,  /* suffix */
            storageDir /* directory */
        )
        val mCurrentPhotoPath = "file:" + image.absolutePath
        fileWithPath.file = image
        fileWithPath.filePath = mCurrentPhotoPath
        fileWithPath.uri = Uri.fromFile(image)
        return fileWithPath
    }

    fun getFilePathFromBitmap(mContext: Context, bitmap: Bitmap, imageExtension: String): String {
        var filePath = ""
        val filesDir = mContext.filesDir
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFile = File(filesDir, timeStamp + imageExtension)
        val os: OutputStream
        try {
            os = FileOutputStream(imageFile)
            if (imageExtension == AppConstants.FileExtension.PNG) bitmap.compress(
                Bitmap.CompressFormat.PNG,
                100,
                os
            ) else bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
            filePath = imageFile.absolutePath
        } catch (e: java.lang.Exception) {

        }
        return filePath
    }

    fun getFileExt(fileName: String): String {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)
    }

    fun getFileName(url: String): String {
        return url.substring(url.lastIndexOf('/') + 1)
    }

    @Throws(IOException::class)
    fun compressImage(path: String, file: File): FileWithPath? {
        if (file.exists()) {
            val fileWithPath: FileWithPath
            val fileSize = file.length()
            val fileSizeInKB = fileSize / 1024
            if (fileSizeInKB > 1024) {
                fileWithPath = ImageUtils.createImageFile(Environment.DIRECTORY_DCIM)
                fileWithPath.uri = Uri.fromFile(fileWithPath.file)
                ImageUtils.compress(
                    path,
                    fileWithPath.file.absolutePath,
                    AppConstants.MAX_IMAGE_WIDTH,
                    AppConstants.MAX_IMAGE_HEIGHT,
                    AppConstants.IMAGE_QUALITY
                )
                return fileWithPath
            }
        }
        return null
    }

    fun getRequestBody(input: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), input)
    }

    fun formatFileSize(size: Double): String {
        var hrSize: String? = null
        val k = size / 1024.0
        val m = size / 1024.0 / 1024.0
        val g = size / 1024.0 / 1024.0 / 1024.0
        val t = size / 1024.0 / 1024.0 / 1024.0 / 1024.0
        val dec = DecimalFormat("0.00")
        hrSize = if (t > 1) {
            dec.format(t) + " TB"
        } else if (g > 1) {
            dec.format(g) + " GB"
        } else if (m > 1) {
            dec.format(m) + " MB"
        } else if (k > 1) {
            dec.format(k) + " KB"
        } else {
            dec.format(size) + " Bytes"
        }
        return hrSize
    }

    fun convertLongDateToString(longDate: Long, dateFormat: String?): String? {
        try {
            val calendar = Calendar.getInstance()
            //calendar.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            calendar.timeInMillis = longDate
            val df2 = SimpleDateFormat(dateFormat)
            return df2.format(calendar.time)
        } catch (e: java.lang.Exception) {
            Log.e("Error LongDateToString", "" + e)
        }
        return ""
    }

    fun getApplicationVersion(mContext: Context): String {
        var pinfo: PackageInfo? = null
        return try {
            pinfo = mContext.packageManager.getPackageInfo(mContext.packageName, 0)
            pinfo.versionName //versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            "0"
        }
    }

    fun opeUrlInBrowser(mContext: Context, url: String) {
        try {
            var mUrl = ""
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                mUrl = "http://$url";
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(mUrl)
            mContext.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setToolbarTextColor(item: MenuItem, title: String, color: Int) {
        val s = SpannableString(title)
        s.setSpan(ForegroundColorSpan(color), 0, s.length, 0)
        item.title = s
    }

    fun getFirebaseUserId(mContext: Context): String {
        return "CUST_" + getUserPreference(mContext)!!.id
    }

    fun getFirebaseTime(date: Date): String {
        val format = SimpleDateFormat(DateFormatsConstants.HH_MM_24, Locale.US)
        return format.format(date)
    }

    fun getFirebaseDate(date: Date): String {
        val format = SimpleDateFormat(DateFormatsConstants.DD_MMMM_YYYY_SPACE, Locale.US)
        var finalDate = ""
        if (DateHelper.isSameDay(date, Date())) {
            finalDate = "Today"
        } else if (DateHelper.isYesterDay(date, Date())) {
            finalDate = "Yesterday"
        } else {
            finalDate = format.format(date)
        }
        return finalDate
    }

    fun checkFirebaseDate(date: Date): String {
        val format = SimpleDateFormat(DateFormatsConstants.DD_MMM_YYYY_SPACE, Locale.US)
        return format.format(date)
    }

    fun showKeyBoard(mContext: Context) {
        val imm: InputMethodManager =
            mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }

    fun hideKeyBoard(mContext: Context) {
        val imm =
            mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    fun getFcmIntent(data: FcmData): Intent {
        var intent: Intent? = null
        val bundle = Bundle()
        bundle.putString(AppConstants.IntentKey.NOTIFICATION_TYPE, data.type)

        intent =
            Intent("com.unjhawalateaadmin.dashboard.ui.activity.InwardOutwardActivity")
        /* when (data.type) {
             "1" -> {
                 intent =
                     Intent("com.imateplus.upt.dashboard.ui.activity.DashBoardActivity")
             }
             else -> intent =
                 Intent("com.imateplus.upt.dashboard.ui.activity.DashBoardActivity")
         }*/
        if (intent != null) {
            intent.putExtras(bundle)
        }
        return intent
    }

    fun shareCaseCalculatorText(activity: Activity, shareText: String) {
        if (!StringHelper.isEmpty(shareText)) {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, shareText)
            intent.type = "text/plain"
            activity.startActivity(intent)
        }
    }

    fun shareSellerDynamicLink(activity: Activity, sellerId: Int) {
        val dynamicLink: DynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse("https://swastikenterprises.com/sellerdetails/$sellerId"))
            .setDomainUriPrefix(AppConstants.DYNAMIC_LINK_PREFIX)
            .setAndroidParameters(
                DynamicLink.AndroidParameters.Builder().build()
            )
            .buildDynamicLink()
        val dynamicLinkUri: Uri = dynamicLink.uri
        FirebaseDynamicLinks.getInstance()
            .createDynamicLink()
            .setLongLink(dynamicLinkUri)
            .buildShortDynamicLink()
            .addOnCompleteListener(
                activity
            ) { task ->
                if (task.isSuccessful) {
                    val shortLink: Uri = task.result!!.shortLink!!
                    val userName = getUserPreference(activity)!!.name
                    val shareText =
                        "$userName shared a best deal with you. Please check and get more exclusive deals.\n$shortLink"
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT, shareText)
                    intent.type = "text/plain"
                    activity.startActivity(intent)
                }
            }
    }

    fun PerfectDecimal(str: String, MAX_BEFORE_POINT: Int, MAX_DECIMAL: Int): String {
        var str = str
        if (str[0] == '.') str = "0$str"
        val max = str.length
        var rFinal = ""
        var after = false
        var i = 0
        var up = 0
        var decimal = 0
        var t: Char
        while (i < max) {
            t = str[i]
            if (t != '.' && after == false) {
                up++
                if (up > MAX_BEFORE_POINT) return rFinal
            } else if (t == '.') {
                after = true
            } else {
                decimal++
                if (decimal > MAX_DECIMAL) return rFinal
            }
            rFinal = rFinal + t
            i++
        }
        return rFinal
    }

    fun capitalizeFirstWordString(str: String): String {
        var retStr = str
        try {
            retStr = str.substring(0, 1).toUpperCase() + str.substring(1)
        } catch (e: Exception) {
        }
        return retStr
    }

    fun capitalizeEveryFirstWordString(str: String): String {
        val str = str.split(" ")
            .joinToString(separator = " ", transform = String::capitalize)
        return str
    }

    fun getPriceTypeName(str: String): String {
        val str = str.replace("per ", "").replace("Per ", "").split(" ")
            .joinToString(separator = " ", transform = String::capitalize)
        return str
    }

    fun shareApplication(mContext: Context) {
        try {
            val appPackageName = mContext.applicationContext.packageName
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_SUBJECT, mContext.getString(R.string.app_name))
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "You can now download the Tile Bazar App on the Playstore. Get start today and reach millions of customers.\n\nhttps://play.google.com/store/apps/details?id=$appPackageName"
            )
            intent.type = "text/plain"
            mContext.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun appInstalledOrNot(mContext: Context, packageName: String): Boolean {
        Log.e("test", "packageName:$packageName");
        return try {
            val pm: PackageManager = mContext.packageManager
            pm.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            false
        }
    }

    fun openWebView(mContext: Context, title: String, url: String) {
        val bundle = Bundle()
        bundle.putString(AppConstants.IntentKey.WEB_URL, url)
        bundle.putString(AppConstants.IntentKey.TITLE, title)
        val intent = Intent(mContext, WebViewActivity::class.java)
        intent.putExtras(bundle)
        mContext.startActivity(intent)
    }

    fun viewFileFromUrl(mContext: Context, url: String) {
        val uri = Uri.parse(url);
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (url.contains(".doc") || url.contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword")
        } else if (url.contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf")
        } else if (url.contains(".ppt") || url.contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint")
        } else if (url.contains(".xls") || url.contains(".xlsx")) {
            Log.e("test", ".xls");
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel")
        } else if (url.contains(".zip") || url.contains(".rar")) {
            // ZIP file
            intent.setDataAndType(uri, "application/zip")
        } else if (url.contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf")
        } else if (url.contains(".wav") || url.contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav")
        } else if (url.contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif")
        } else if (url.contains(".jpg") || url.contains(".jpeg") || url.contains(".png")) {
            Log.e("test", "image/*");
            // JPG file
            intent.setDataAndType(uri, "image/*")
        } else if (url.contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain")
        } else if (url.contains(".3gp") || url.contains(".mpg") || url.contains(".mpeg") || url.contains(
                ".mpe"
            ) || url.contains(".mp4") || url.contains(".avi")
        ) {
            // Video files
            intent.setDataAndType(uri, "video/*")
        } else {
            //if you want you can also define the intent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "*/*")
        }

        try {
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            mContext.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                mContext,
                mContext.getString(R.string.msg_no_application_found),
                Toast.LENGTH_LONG
            ).show()
        } catch (e: java.lang.NullPointerException) {

        }
    }

    fun viewFile(mContext: Context, url: String) {
        val uri =
            FileProvider.getUriForFile(mContext, mContext.packageName + ".provider", File(url))
        val intent = Intent(Intent.ACTION_VIEW)
        if (url.contains(".doc") || url.contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword")
        } else if (url.contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf")
        } else if (url.contains(".ppt") || url.contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint")
        } else if (url.contains(".xls") || url.contains(".xlsx")) {
            Log.e("test", ".xls");
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel")
        } else if (url.contains(".zip") || url.contains(".rar")) {
            // ZIP file
            intent.setDataAndType(uri, "application/zip")
        } else if (url.contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf")
        } else if (url.contains(".wav") || url.contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav")
        } else if (url.contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif")
        } else if (url.contains(".jpg") || url.contains(".jpeg") || url.contains(".png")) {
            Log.e("test", "image/*");
            // JPG file
            intent.setDataAndType(uri, "image/*")
        } else if (url.contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain")
        } else if (url.contains(".3gp") || url.contains(".mpg") || url.contains(".mpeg") || url.contains(
                ".mpe"
            ) || url.contains(".mp4") || url.contains(".avi")
        ) {
            // Video files
            intent.setDataAndType(uri, "video/*")
        } else {
            //if you want you can also define the intent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "*/*")
        }
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        try {
            mContext.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                mContext,
                mContext.getString(R.string.msg_no_application_found),
                Toast.LENGTH_LONG
            ).show()
        } catch (e: NullPointerException) {
        }

    }

    fun copiedText(activity: Activity, text: String) {
        if (!StringHelper.isEmpty(text)) {
            val clipboardManager =
                activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", text)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(
                activity,
                activity.getText(R.string.msg_text_copied),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun showDateRangeDialog(
        mContext: Context,
        inputFromDate: String?,
        inputToDate: String?,
        format: String?,
        fragmentManager: FragmentManager?,
        listener: SelectDateRangeListener?
    ) {
        var startDate: Long = 0
        var endDate: Long = 0
        val dateFormat = SimpleDateFormat(format, Locale.US)
        try {
            if (!StringHelper.isEmpty(inputFromDate)) {
                val date = dateFormat.parse(inputFromDate)
                startDate = date.time + 1000 * 60 * 60 * 24
            }
            if (!StringHelper.isEmpty(inputToDate)) {
                val date = dateFormat.parse(inputToDate)
                endDate = date.time + 1000 * 60 * 60 * 24
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker()
        val constraintsBuilderRange = CalendarConstraints.Builder()
        val dateValidatorMin: DateValidator = DateValidatorPointForward.from(0)
        val dateValidatorMax: DateValidator = DateValidatorPointBackward.before(Date().time)
        val listValidators = ArrayList<DateValidator>()
        listValidators.add(dateValidatorMin)
        listValidators.add(dateValidatorMax)
        val validators = CompositeDateValidator.allOf(listValidators)
        constraintsBuilderRange.setValidator(validators)
        materialDateBuilder.setCalendarConstraints(constraintsBuilderRange.build())
        materialDateBuilder.setTheme(R.style.ThemeMaterialCalendar)
        val selection = Pair(startDate, endDate)
        if (startDate != 0L && endDate != 0L) materialDateBuilder.setSelection(selection)
        materialDateBuilder.setTitleText(mContext.getString(R.string.lbl_filter_by_date))
        val materialDatePicker = materialDateBuilder.build()
        materialDatePicker.show(fragmentManager!!, "MATERIAL_DATE_PICKER")
        materialDatePicker.addOnPositiveButtonClickListener(
            MaterialPickerOnPositiveButtonClickListener { selection: Pair<Long?, Long?> ->
                val fromDateFilterCalendar = Calendar.getInstance()
                fromDateFilterCalendar.time = Date(selection.first!!)
                val fromDate = dateFormat.format(fromDateFilterCalendar.time)

                val toDateFilterCalendar = Calendar.getInstance()
                toDateFilterCalendar.time = Date(selection.second!!)
                val toDate = dateFormat.format(toDateFilterCalendar.time)

                listener?.onSelectDate(fromDate, toDate)
            } as MaterialPickerOnPositiveButtonClickListener<Pair<Long?, Long?>>)
    }

    fun openPlayStore(context: Context) {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + packageInfo.packageName)
                )
            )
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + packageInfo!!.packageName)
                )
            )
        }
    }

    fun getDeviceUniqueId(mContext: Context): String {
        return Settings.Secure.getString(mContext.contentResolver, Settings.Secure.ANDROID_ID)
    }
}