package com.unjhawalateaadmin.authentication.ui.activity


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.authentication.data.model.SignUpRequest
import com.unjhawalateaadmin.authentication.ui.viewmodel.AuthenticationViewModel
import com.unjhawalateaadmin.common.data.model.RegisterConfigurationResponse
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.databinding.ActivitySignUpBinding
import com.imateplus.utilities.utils.AlertDialogHelper
import com.imateplus.utilities.utils.StringHelper
import com.imateplus.utilities.utils.ValidationUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mContext: Context
    private val authenticationViewModel: AuthenticationViewModel by viewModel()
    lateinit var signUpRequest: SignUpRequest
    private lateinit var configurationDetails: RegisterConfigurationResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setStatusBarColor()
        setupToolbar(getString(R.string.sign_up), true)
        mContext = this

        setSignUpObservers()
        registerConfigurationResponseObservers()

        binding.txtLogin.setOnClickListener(this)
        binding.txtSignUp.setOnClickListener(this)
        binding.edtState.setOnClickListener(this)

        signUpRequest = SignUpRequest()
        binding.signUpRequest = signUpRequest

        showProgressDialog(mContext, "")
        authenticationViewModel.getRegisterConfigurationResponse()

//        val inputText = binding.textInput.editText?.text.toString()

//        binding.textInput.editText?.doOnTextChanged { inputText, _, _, _ ->
//            // Respond to input text change
//        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txtLogin -> {
                moveActivity(mContext, LoginActivity::class.java, true, true, null)
            }
            R.id.txtSignUp ->
                if (valid()) {
                    showProgressDialog(mContext, "")
                    authenticationViewModel.register(signUpRequest)
                }
        }
    }

    private fun setSignUpObservers() {
        authenticationViewModel.baseResponse.observe(this) { response ->
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
                        bundle.putString(
                            AppConstants.IntentKey.PHONE_NUMBER,
                            binding.edtPhoneNumber.text.toString().trim()
                        )
                        moveActivity(mContext, VerifyOtpActivity::class.java, true, false, bundle)
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun registerConfigurationResponseObservers() {
        authenticationViewModel.registerConfigurationResponse.observe(this) { response ->
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
                        configurationDetails = response
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

        if (!ValidationUtil.isEmptyEditText(signUpRequest.city)) {
            binding.layoutCity.error = null
            binding.layoutCity.isErrorEnabled = false
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtCity, binding.layoutCity,
                getString(R.string.error_empty_city)
            )
            valid = false
        }

        if (signUpRequest.state_id != 0) {
            binding.layoutState.error = null
            binding.layoutState.isErrorEnabled = false
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtState, binding.layoutState,
                getString(R.string.error_empty_state)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(signUpRequest.company_name)) {
            binding.layoutCompanyName.error = null
            binding.layoutCompanyName.isErrorEnabled = false
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtCompanyName, binding.layoutCompanyName,
                getString(R.string.error_empty_company_name)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(signUpRequest.phone_number)) {
            if (!ValidationUtil.isValidPhoneNumberRange(
                    StringHelper.removeZeroOnFirstLetter(
                        signUpRequest.phone_number
                    )
                )
            ) {
                ValidationUtil.setErrorIntoInputTextLayout(
                    binding.edtPhoneNumber, binding.layoutPhoneNumber,
                    getString(R.string.error_phone_number_min_length)
                )
                valid = false
            } else {
                binding.layoutPhoneNumber.isErrorEnabled = false
                binding.layoutPhoneNumber.error = null
            }
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtPhoneNumber, binding.layoutPhoneNumber,
                getString(R.string.enter_phone_number)
            )
            valid = false
        }

        if (!ValidationUtil.isEmptyEditText(signUpRequest.name)) {
            binding.layoutFullName.error = null
            binding.layoutFullName.isErrorEnabled = false
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(
                binding.edtFullName, binding.layoutFullName,
                getString(R.string.error_name_mandatory)
            )
            valid = false
        }

        return valid
    }


}