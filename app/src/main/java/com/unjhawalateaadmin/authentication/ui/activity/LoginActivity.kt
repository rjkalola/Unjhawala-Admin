package com.unjhawalateaadmin.authentication.ui.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.authentication.data.model.LoginRequest
import com.unjhawalateaadmin.authentication.ui.viewmodel.AuthenticationViewModel
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.databinding.ActivityLoginBinding
import com.imateplus.utilities.utils.AlertDialogHelper
import com.imateplus.utilities.utils.ValidationUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mContext: Context
    private val authenticationViewModel: AuthenticationViewModel by viewModel()
    lateinit var loginRequest: LoginRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setStatusBarColor(resources.getColor(R.color.colorYellow))
        setupToolbar("", false)
        mContext = this
        setLoginObservers()

        binding.txtLogin.setOnClickListener(this)

        loginRequest = LoginRequest()
        binding.loginRequest = loginRequest
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txtLogin
            -> {
                if (valid()) {
                    showProgressDialog(mContext, "")
                    authenticationViewModel.login(binding.edtMobileNumber.text.toString().trim())
                }
            }
        }
    }

    private fun setLoginObservers() {
        authenticationViewModel.loginResponse.observe(this) { response ->
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
                        val bundle = Bundle()
                        bundle.putString(AppConstants.IntentKey.USER_ID,response.Data.id)
                        bundle.putString(AppConstants.IntentKey.PHONE_NUMBER,response.Data.phone_number)
                        moveActivity(mContext, VerifyOtpActivity::class.java, true, true, bundle)
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun valid(): Boolean {
        var valid = true

        if (!ValidationUtil.isEmptyEditText(binding.edtMobileNumber.text.toString().trim())) {
            if (binding.edtMobileNumber.text.toString().trim().length == 10) {
                binding.layoutMobileNumber.isErrorEnabled = false
                binding.edtMobileNumber.error = null
            }else{
                ValidationUtil.setErrorIntoInputTextLayout(
                    binding.edtMobileNumber, binding.layoutMobileNumber,
                    getString(R.string.error_phone_number_min_length)
                )
                valid = false
            }
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtMobileNumber, binding.layoutMobileNumber,
                getString(R.string.enter_phone_number)
            )
            valid = false
        }

        return valid
    }

}