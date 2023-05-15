package com.unjhawalateaadmin.authentication.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unjhawalateaadmin.authentication.data.model.SignUpRequest
import com.unjhawalateaadmin.authentication.data.model.StoreProfilePhotoResponse
import com.unjhawalateaadmin.authentication.data.model.UserResponse
import com.unjhawalateaadmin.authentication.data.repository.AuthenticationRepository
import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.data.model.RegisterConfigurationResponse
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.common.utils.traceErrorException
import com.unjhawalateaadmin.dashboard.data.model.MemberDetailsResponse
import com.imateplus.utilities.utils.StringHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import java.io.File
import java.util.concurrent.CancellationException

class AuthenticationViewModel(val authenticationRepository: AuthenticationRepository) :
    ViewModel() {
    val loginResponse = MutableLiveData<UserResponse>()
    val resendOTPResponse = MutableLiveData<UserResponse>()
    val customerDetailsResponse = MutableLiveData<UserResponse>()
    val baseResponse = MutableLiveData<BaseResponse>()
    val storeDeviceTokenResponse = MutableLiveData<BaseResponse>()
    val setPinResponse = MutableLiveData<BaseResponse>()
    val registerConfigurationResponse = MutableLiveData<RegisterConfigurationResponse>()
    val storeProfileImageResponse = MutableLiveData<StoreProfilePhotoResponse>()

    /*fun login(loginRequest: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userResponse = authenticationRepository.login(loginRequest)
                withContext(Dispatchers.Main) {
                    loginResponse.value = userResponse
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }*/

    fun login(mobileNumber: String) {
        val mobileNumberBody: RequestBody = AppUtils.getRequestBody(mobileNumber)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userResponse = authenticationRepository.login(mobileNumberBody)
                withContext(Dispatchers.Main) {
                    loginResponse.value = userResponse
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun resendOTP(mobileNumber: String) {
        val mobileNumberBody: RequestBody = AppUtils.getRequestBody(mobileNumber)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userResponse = authenticationRepository.resendOTP(mobileNumberBody)
                withContext(Dispatchers.Main) {
                    resendOTPResponse.value = userResponse
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun validateOtp(userId: String, otp: String,deviceId:String) {
        val userIdBody: RequestBody = AppUtils.getRequestBody(userId)
        val otpBody: RequestBody = AppUtils.getRequestBody(otp)
        val deviceIdBody: RequestBody = AppUtils.getRequestBody(deviceId)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userResponse = authenticationRepository.validateOtp(userIdBody, otpBody,deviceIdBody)
                withContext(Dispatchers.Main) {
                    loginResponse.value = userResponse
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun setLoginPin(userId: String, mPin: String) {
        val userIdBody: RequestBody = AppUtils.getRequestBody(userId)
        val mPinBody: RequestBody = AppUtils.getRequestBody(mPin)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    authenticationRepository.setLoginPin(userIdBody, mPinBody)
                withContext(Dispatchers.Main) {
                    loginResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun quickLogin(userId: String, mPin: String) {
        val userIdBody: RequestBody = AppUtils.getRequestBody(userId)
        val mPinBody: RequestBody = AppUtils.getRequestBody(mPin)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    authenticationRepository.quickLogin(userIdBody, mPinBody)
                withContext(Dispatchers.Main) {
                    loginResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun resetLoginPin(userId: String, type: String) {
        val userIdBody: RequestBody = AppUtils.getRequestBody(userId)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    authenticationRepository.resetLoginPin(userIdBody)
                withContext(Dispatchers.Main) {
                    baseResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun storeDeviceTokenResponse(deviceToken: String) {
        val deviceTokenBody: RequestBody = AppUtils.getRequestBody(deviceToken)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    authenticationRepository.storeDeviceToken(deviceTokenBody)
                withContext(Dispatchers.Main) {
                    storeDeviceTokenResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun register(signUpRequest: SignUpRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authenticationRepository.register(signUpRequest)
                withContext(Dispatchers.Main) {
                    baseResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getRegisterConfigurationResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authenticationRepository.getRegisterConfiguration()
                withContext(Dispatchers.Main) {
                    registerConfigurationResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getCustomerDetails(customerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authenticationRepository.getCustomerDetails(customerId)
                withContext(Dispatchers.Main) {
                    customerDetailsResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    /* fun editProfileResponse(request: User) {
         var imageFileBody: MultipartBody.Part? = null
         val name: RequestBody = AppUtils.getRequestBody(request.name)
         val address: RequestBody = AppUtils.getRequestBody(request.address)
         val email: RequestBody = AppUtils.getRequestBody(request.email)
         val company_name: RequestBody = AppUtils.getRequestBody(request.company_name)
         val state_id: RequestBody = AppUtils.getRequestBody(request.state_id.toString())
         val city: RequestBody = AppUtils.getRequestBody(request.city)

         if (!StringHelper.isEmpty(request.image) && !request.image!!.startsWith("http")) {
             val file = File(request.image)
             val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
             imageFileBody = MultipartBody.Part.createFormData("image", file.name, requestBody)
         }

         viewModelScope.launch(Dispatchers.IO) {
             try {
                 val response = authenticationRepository.editProfile(
                     imageFileBody,
                     name,
                     address,
                     email,
                     company_name,
                     state_id,
                     city,
                 )
                 withContext(Dispatchers.Main) {
                     loginResponse.value = response
                 }
             } catch (e: JSONException) {
                 e.printStackTrace()
                 traceErrorException(e)
             } catch (e: CancellationException) {
                 e.printStackTrace()
                 traceErrorException(e)
             } catch (e: Exception) {

                 traceErrorException(e)
             }
         }
     }*/

    fun verifyPhoneNumberOtp(phoneNumber: String, otp: String) {
        val phoneNumberBody: RequestBody = AppUtils.getRequestBody(phoneNumber)
        val otpBody: RequestBody = AppUtils.getRequestBody(otp)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    authenticationRepository.verifyPhoneNumberOtp(phoneNumberBody, otpBody)
                withContext(Dispatchers.Main) {
                    loginResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun resendPhoneNumberOtp(phoneNumber: String) {
        val phoneNumberBody: RequestBody = AppUtils.getRequestBody(phoneNumber)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    authenticationRepository.resendPhoneNumberOtp(phoneNumberBody)
                withContext(Dispatchers.Main) {
                    baseResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun storeProfileImageResponse(image: String) {
        var imageFileBody: MultipartBody.Part? = null

        if (!StringHelper.isEmpty(image) && !image.startsWith("http")) {
            val file = File(image)
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            imageFileBody = MultipartBody.Part.createFormData("image", file.name, requestBody)
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authenticationRepository.storeProfileImage(
                    imageFileBody,
                )
                withContext(Dispatchers.Main) {
                    storeProfileImageResponse.value = response
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                traceErrorException(e)
            } catch (e: CancellationException) {
                e.printStackTrace()
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

}