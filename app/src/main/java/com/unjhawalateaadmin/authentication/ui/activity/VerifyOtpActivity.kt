package com.unjhawalateaadmin.authentication.ui.activity


import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.authentication.ui.viewmodel.AuthenticationViewModel
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.dashboard.ui.activity.DashBoardActivity
import com.unjhawalateaadmin.databinding.ActivityVerifyOtpBinding
import com.imateplus.utilities.utils.AlertDialogHelper
import com.imateplus.utilities.utils.StringHelper
import com.imateplus.utilities.utils.ToastHelper
import org.koin.androidx.viewmodel.ext.android.viewModel


class VerifyOtpActivity : BaseActivity(), View.OnClickListener, View.OnKeyListener {
    private lateinit var binding: ActivityVerifyOtpBinding
    private lateinit var mContext: Context
    private val authenticationViewModel: AuthenticationViewModel by viewModel()
    private var userId = ""
    private var phoneNumber = ""
    lateinit var code: String
    private var cTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_otp)
        setStatusBarColor(resources.getColor(R.color.colorYellow))
        setupToolbar("", true)
        mContext = this
        verifyOtpResponse()
        resendOtpResponse()

        binding.edtVerifyCode1.addTextChangedListener(
            GenericTextWatcher(
                binding.edtVerifyCode1,
                binding
            )
        )
        binding.edtVerifyCode2.addTextChangedListener(
            GenericTextWatcher(
                binding.edtVerifyCode2,
                binding
            )
        )
        binding.edtVerifyCode3.addTextChangedListener(
            GenericTextWatcher(
                binding.edtVerifyCode3,
                binding
            )
        )

        binding.edtVerifyCode4.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(editable: Editable?) {
                    val text = editable.toString()
                    if (StringHelper.isEmpty(text)) {
                        binding.edtVerifyCode3.requestFocus()
                    } else {
//                        if (validateCode(true)) {
//                            showProgressDialog(mContext, "")
//                            authenticationViewModel.validateOtp(
//                                userId,
//                                code
//                            )
//                        }
                    }
                }
            }
        )

        binding.edtVerifyCode1.setOnKeyListener(this)
        binding.edtVerifyCode2.setOnKeyListener(this)
        binding.edtVerifyCode3.setOnKeyListener(this)
        binding.edtVerifyCode4.setOnKeyListener(this)
        binding.txtChangeNumber.setOnClickListener(this)

        binding.txtSubmit.setOnClickListener(this)
        binding.txtReSendOTP.setOnClickListener(this)

        getIntentData()
    }

    private fun getIntentData() {
        if (intent.extras != null && intent.hasExtra(AppConstants.IntentKey.USER_ID))
            userId = intent.extras!!.getString(AppConstants.IntentKey.USER_ID, "")

        if (intent.extras != null && intent.hasExtra(AppConstants.IntentKey.PHONE_NUMBER))
            phoneNumber = intent.extras!!.getString(AppConstants.IntentKey.PHONE_NUMBER, "")

        if (!StringHelper.isEmpty(phoneNumber))
            binding.txtDisplayOtpPhoneNumber.text =
                String.format(getString(R.string.display_otp_sent_to_mobile_number), phoneNumber)

        startTimer()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txtSubmit -> {
                if (validateCode(false)) {
                    showProgressDialog(mContext, "")
                    authenticationViewModel.validateOtp(
                        userId,
                        code
                    )
                }
            }
            R.id.txtReSendOTP -> {
//                showProgressDialog(mContext, "")
                authenticationViewModel.resendOTP(phoneNumber)
            }
            R.id.txtChangeNumber -> {
                moveActivity(mContext, LoginActivity::class.java, true, false, null)
            }

        }
    }

    private fun verifyOtpResponse() {
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
                        AppUtils.setUserPreference(mContext, response.Data)
//                        moveActivity(mContext, SetPinActivity::class.java, true, true, null)
                        moveActivity(mContext, DashBoardActivity::class.java, true, true, null)
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun resendOtpResponse() {
        authenticationViewModel.resendOTPResponse.observe(this) { response ->
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
                        ToastHelper.normal(
                            mContext,
                            getString(R.string.msg_otp_resend),
                            Toast.LENGTH_SHORT,
                            false
                        )
                        startTimer()
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun validateCode(isAuto: Boolean): Boolean {
        if (StringHelper.isEmpty(binding.edtVerifyCode1.text.toString().trim())) {
            if (!isAuto)
                ToastHelper.error(
                    mContext,
                    getString(R.string.error_empty_otp),
                    Toast.LENGTH_SHORT,
                    false
                )
            return false
        } else if (StringHelper.isEmpty(binding.edtVerifyCode2.text.toString().trim())) {
            if (!isAuto)
                ToastHelper.error(
                    mContext,
                    getString(R.string.error_empty_otp),
                    Toast.LENGTH_SHORT,
                    false
                )
            return false
        } else if (StringHelper.isEmpty(binding.edtVerifyCode3.text.toString().trim())) {
            if (!isAuto)
                ToastHelper.error(
                    mContext,
                    getString(R.string.error_empty_otp),
                    Toast.LENGTH_SHORT,
                    false
                )
            return false
        } else if (StringHelper.isEmpty(binding.edtVerifyCode4.text.toString().trim())) {
            if (!isAuto)
                ToastHelper.error(
                    mContext,
                    getString(R.string.error_empty_otp),
                    Toast.LENGTH_SHORT,
                    false
                )
            return false
        } else {
            code = (binding.edtVerifyCode1.text.toString()
                    + binding.edtVerifyCode2.text.toString()
                    + binding.edtVerifyCode3.text.toString()
                    + binding.edtVerifyCode4.text.toString())
            //            setOtp(code);
        }
        return true
    }

    private class GenericTextWatcher(val view: View, val binding: ActivityVerifyOtpBinding) :
        TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(editable: Editable) {
            val text = editable.toString()
            when (view.id) {
                R.id.edtVerifyCode_1 -> if (!StringHelper.isEmpty(text)) {
                    binding.edtVerifyCode2.requestFocus()
                }
                R.id.edtVerifyCode_2 -> if (StringHelper.isEmpty(text)) {
                    binding.edtVerifyCode1.requestFocus()
                } else {
                    binding.edtVerifyCode3.requestFocus()
                }
                R.id.edtVerifyCode_3 -> if (StringHelper.isEmpty(text)) {
                    binding.edtVerifyCode2.requestFocus()
                } else {
                    binding.edtVerifyCode4.requestFocus()
                }
                R.id.edtVerifyCode_4 -> if (StringHelper.isEmpty(text)) {
                    binding.edtVerifyCode3.requestFocus()
                } else {
                    binding.edtVerifyCode4.requestFocus()
                }
            }
        }
    }

    override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
        if (event.action != KeyEvent.ACTION_DOWN) return false
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (v.id == R.id.edtVerifyCode_2) {
                binding.edtVerifyCode1.requestFocus()
            } else if (v.id == R.id.edtVerifyCode_3) {
                binding.edtVerifyCode2.requestFocus()
            } else if (v.id == R.id.edtVerifyCode_4) {
                binding.edtVerifyCode3.requestFocus()
            }
        } else if (keyCode == KeyEvent.KEYCODE_0 || keyCode == KeyEvent.KEYCODE_1 || keyCode == KeyEvent.KEYCODE_2 || keyCode == KeyEvent.KEYCODE_3 || keyCode == KeyEvent.KEYCODE_4 || keyCode == KeyEvent.KEYCODE_5 || keyCode == KeyEvent.KEYCODE_6 || keyCode == KeyEvent.KEYCODE_7 || keyCode == KeyEvent.KEYCODE_8 || keyCode == KeyEvent.KEYCODE_9) {
            if (v.id == R.id.edtVerifyCode_1) {
                if (binding.edtVerifyCode1.text.isNotEmpty()) {
                    binding.edtVerifyCode1.setText(getNumberKey(keyCode).toString())
                    binding.edtVerifyCode2.requestFocus()
                    binding.edtVerifyCode1.setSelection(binding.edtVerifyCode1.text.length)
                }
            } else if (v.id == R.id.edtVerifyCode_2) {
                if (binding.edtVerifyCode2.text.isNotEmpty()) {
                    binding.edtVerifyCode2.setText(getNumberKey(keyCode).toString())
                    binding.edtVerifyCode3.requestFocus()
                    binding.edtVerifyCode2.setSelection(binding.edtVerifyCode2.text.length)
                }
            } else if (v.id == R.id.edtVerifyCode_3) {
                if (binding.edtVerifyCode3.text.isNotEmpty()) {
                    binding.edtVerifyCode3.setText(getNumberKey(keyCode).toString())
                    binding.edtVerifyCode4.requestFocus()
                    binding.edtVerifyCode3.setSelection(binding.edtVerifyCode3.text.length)
                }
            } else if (v.id == R.id.edtVerifyCode_4) {
                if (binding.edtVerifyCode4.text.isNotEmpty()) {
                    binding.edtVerifyCode4.setText(getNumberKey(keyCode).toString())
                    binding.edtVerifyCode4.setSelection(binding.edtVerifyCode4.text.length)
                }
            }
        }
        return false
    }

    private fun getNumberKey(keycode: Int): Int {
        var key = 0
        when (keycode) {
            KeyEvent.KEYCODE_0 -> key = 0
            KeyEvent.KEYCODE_1 -> key = 1
            KeyEvent.KEYCODE_2 -> key = 2
            KeyEvent.KEYCODE_3 -> key = 3
            KeyEvent.KEYCODE_4 -> key = 4
            KeyEvent.KEYCODE_5 -> key = 5
            KeyEvent.KEYCODE_6 -> key = 6
            KeyEvent.KEYCODE_7 -> key = 7
            KeyEvent.KEYCODE_8 -> key = 8
            KeyEvent.KEYCODE_9 -> key = 9
        }
        return key
    }

    override fun onBackPressed() {

    }

    override fun onDestroy() {
        super.onDestroy()
        cancelTimer()
    }

    fun startTimer() {
        binding.txtReSendOTPCounter.visibility = View.VISIBLE
        binding.txtReSendOTP.visibility = View.GONE
        cTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.txtReSendOTPCounter.text =
                    String.format(getString(R.string.display_resend_otp_counter),(millisUntilFinished/1000).toString())
            }
            override fun onFinish() {
                binding.txtReSendOTPCounter.visibility = View.GONE
                binding.txtReSendOTP.visibility = View.VISIBLE
            }
        }
        cTimer?.start()
    }

    fun cancelTimer() {
        if (cTimer != null) cTimer!!.cancel()
    }

}