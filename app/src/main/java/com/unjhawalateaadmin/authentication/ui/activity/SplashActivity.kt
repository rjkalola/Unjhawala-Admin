package com.unjhawalateaadmin.authentication.ui.activity


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.unjhawalateaadmin.MyApplication
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.common.data.model.FcmData
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.dashboard.ui.activity.DashBoardActivity
import com.unjhawalateaadmin.databinding.ActivitySplashBinding
import com.imateplus.utilities.utils.StringHelper


class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var mContext: Context;
    private val splashTimeOut: Long = 2500
    private var linkId: String? = null
    private var linkType: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
//        setStatusBarColor()
        setStatusBarColor(resources.getColor(R.color.colorLoginScreenBg))
        mContext = this

        /*  FirebaseDynamicLinks.getInstance()
              .getDynamicLink(intent)
              .addOnSuccessListener(
                  this
              ) { pendingDynamicLinkData ->
                  if (pendingDynamicLinkData != null) {
                      val deepLink = pendingDynamicLinkData.link
                      if (deepLink != null) {
                          var link = deepLink.path.toString();
                          if (link.startsWith("/"))
                              link = link.substring(1, link.length)
                          val paths = link.split("/").toTypedArray()
                          if (paths.isNotEmpty() && paths.size > 1) {
                              linkType = paths[0]
                              linkId = paths[1]
                          }
                          Log.e("test", "linkId:$linkId");
                      }
                  }
              }
              .addOnFailureListener(
                  this
              ) { e ->

              }
  */
        Handler(Looper.getMainLooper()).postDelayed({
            val userInfo: String =
                MyApplication().preferenceGetString(AppConstants.SharedPrefKey.USER_INFO, "")

            if (intent != null && intent.extras != null) {
                if (!StringHelper.isEmpty(userInfo)) {
                    moveToNotificationIntent()
                } else {
                    moveActivity(mContext, LoginActivity::class.java, true, true, null)
                }
            } else {
                if (StringHelper.isEmpty(userInfo) || userInfo.equals("null", ignoreCase = true)) {
                    moveActivity(mContext, LoginActivity::class.java, true, true, null)
                } else {
//                    moveActivity(mContext, SetPinActivity::class.java, true, true, null)
                    moveActivity(mContext, DashBoardActivity::class.java, true, true, null)
                }
            }
        }, splashTimeOut)

    }

    private fun moveToNotificationIntent() {
        try {
            val bundle = intent.extras
            val data = FcmData()
            if (bundle != null) {
                bundle.putBoolean(AppConstants.IntentKey.IS_FROM_NOTIFICATION, true)
                data.body = bundle.getString("body")
                data.type = bundle.getString("notification_type")
                data.timestamp = bundle.getString("timestamp")
                data.title = bundle.getString("title")
            }
//            moveActivity(mContext, SetPinActivity::class.java, true, true, bundle)
            moveActivity(mContext, DashBoardActivity::class.java, true, true, bundle)
//            var intent: Intent? = null
//            intent = AppUtils.getFcmIntent(data)
//            intent.putExtra(AppConstants.IntentKey.IS_FROM_NOTIFICATION, true)
//            startActivity(intent)
//            finish()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}