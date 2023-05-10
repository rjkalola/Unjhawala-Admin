package com.unjhawalateaadmin.authentication.ui.activity

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.unjhawalateaadmin.MyApplication
import com.unjhawalateaadmin.R
import com.unjhawalateaadmin.authentication.data.model.LoginRequest
import com.unjhawalateaadmin.authentication.ui.viewmodel.AuthenticationViewModel
import com.unjhawalateaadmin.common.ui.activity.BaseActivity
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.dashboard.ui.activity.DashBoardActivity
import com.unjhawalateaadmin.databinding.ActivitySetPinBinding
import com.imateplus.utilities.utils.AlertDialogHelper
import com.imateplus.utilities.utils.StringHelper
import com.imateplus.utilities.utils.ToastHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class SetPinActivity : BaseActivity(), View.OnClickListener, View.OnKeyListener {
    private lateinit var binding: ActivitySetPinBinding
    private lateinit var mContext: Context
    private val authenticationViewModel: AuthenticationViewModel by viewModel()
    lateinit var loginRequest: LoginRequest
    lateinit var code: String
    var isFromNotification = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_pin)
        setStatusBarColor(resources.getColor(R.color.colorYellow))
        setupToolbar("", false)
        mContext = this
        setPinResponseObservers()
        resetPinResponseObservers()

        binding.txtSubmit.setOnClickListener(this)

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
//                        binding.edtVerifyCode4.requestFocus()
                        if (validateCode(true)) {
                            apiCall()
                        }
                    }
                }
            }
        )

        binding.edtVerifyCode1.setOnKeyListener(this)
        binding.edtVerifyCode2.setOnKeyListener(this)
        binding.edtVerifyCode3.setOnKeyListener(this)
        binding.edtVerifyCode4.setOnKeyListener(this)

        loginRequest = LoginRequest()
        binding.loginRequest = loginRequest

        if (AppUtils.getUserPreference(mContext)?.is_mpin == 0) {
            binding.txtTitle1.text = getString(R.string.set_mpin)
            binding.txtTitle2.text = getString(R.string.set_mpin_note)
            binding.txtSubmit.text = getString(R.string.set)
        } else {
            binding.txtTitle1.text = getString(R.string.enter_mpin)
            binding.txtTitle2.text = getString(R.string.enter_mpin_note)
            binding.txtSubmit.text = getString(R.string.submit)
        }

        getIntentData()
    }

    private fun getIntentData() {
        if (intent != null && intent.extras != null) {
            isFromNotification =
                intent.getBooleanExtra(AppConstants.IntentKey.IS_FROM_NOTIFICATION, false)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txtSubmit -> {
                if (validateCode(false)) {
                    apiCall()
                }
            }
        }
    }

    private fun apiCall() {
        showProgressDialog(mContext, "")
         if (AppUtils.getUserPreference(mContext)?.is_mpin == 0) {
             authenticationViewModel.setLoginPin(
                 AppUtils.getUserPreference(mContext)?.id!!,
                 code,
             )
         } else {
             authenticationViewModel.quickLogin(
                 AppUtils.getUserPreference(mContext)?.id!!,
                 code,
             )
         }
    }

    private fun setPinResponseObservers() {
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
//                        if (isFromNotification) {
//                            val bundle = Bundle()
//                            bundle.putBoolean(AppConstants.IntentKey.IS_FROM_NOTIFICATION, true)
//                            moveActivity(
//                                mContext,
//                                InwardOutwardActivity::class.java,
//                                true,
//                                true,
//                                bundle
//                            )
//                        } else {
                            moveActivity(mContext, DashBoardActivity::class.java, true, true, null)
//                        }
                    } else {
                        AppUtils.handleUnauthorized(mContext, response)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun resetPinResponseObservers() {
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
                        AlertDialogHelper.showDialog(
                            mContext,
                            null,
                            response.Message,
                            mContext.getString(R.string.close),
                            null,
                            false,
                            null,
                            0
                        )
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
                    getString(R.string.error_empty_pin),
                    Toast.LENGTH_SHORT,
                    false
                )
            return false
        } else if (StringHelper.isEmpty(binding.edtVerifyCode2.text.toString().trim())) {
            if (!isAuto)
                ToastHelper.error(
                    mContext,
                    getString(R.string.error_empty_pin),
                    Toast.LENGTH_SHORT,
                    false
                )
            return false
        } else if (StringHelper.isEmpty(binding.edtVerifyCode3.text.toString().trim())) {
            if (!isAuto)
                ToastHelper.error(
                    mContext,
                    getString(R.string.error_empty_pin),
                    Toast.LENGTH_SHORT,
                    false
                )
            return false
        } else if (StringHelper.isEmpty(binding.edtVerifyCode4.text.toString().trim())) {
            if (!isAuto)
                ToastHelper.error(
                    mContext,
                    getString(R.string.error_empty_pin),
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

    private class GenericTextWatcher(val view: View, val binding: ActivitySetPinBinding) :
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
}