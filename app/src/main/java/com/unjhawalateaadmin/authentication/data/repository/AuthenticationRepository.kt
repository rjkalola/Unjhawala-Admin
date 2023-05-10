package com.unjhawalateaadmin.authentication.data.repository

import com.unjhawalateaadmin.authentication.data.model.*
import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.data.model.RegisterConfigurationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthenticationRepository {
//    suspend fun login(loginRequest: LoginRequest): UserResponse

    suspend fun login(mobile_number: RequestBody): UserResponse

    suspend fun resendOTP(mobile_number: RequestBody): UserResponse

    suspend fun validateOtp(
        user_id: RequestBody, otp: RequestBody
    ): UserResponse

    suspend fun setLoginPin(
        user_id: RequestBody,
        mpin: RequestBody,
    ): UserResponse

    suspend fun quickLogin(
        user_id: RequestBody,
        mpin: RequestBody,
    ): UserResponse

    suspend fun resetLoginPin(
        user_id: RequestBody,
    ): BaseResponse

    suspend fun storeDeviceToken(
        device_token: RequestBody,
    ): BaseResponse

    suspend fun register(signUpRequest: SignUpRequest): BaseResponse
    suspend fun getPhoneExtension(countryCode: String): PhoneExtensionResponse
    suspend fun getCountries(): CountryResponse

    suspend fun editProfile(
        image: MultipartBody.Part?,
        name: RequestBody,
        address: RequestBody,
        email: RequestBody,
        company_name: RequestBody,
        state_id: RequestBody,
        city: RequestBody,
    ): UserResponse

    suspend fun getCustomerDetails(customerId: Int): UserResponse
    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): BaseResponse
    suspend fun forgotPasswordUserExist(
        email: RequestBody,
        guard: RequestBody
    ): ForgotPasswordUserExistResponse

    suspend fun forgotPasswordSendOtp(forgotPasswordSendOtpRequest: ForgotPasswordSendOtpRequest): BaseResponse
    suspend fun forgotPasswordVerifyOtp(forgotPasswordSendOtpRequest: ForgotPasswordVerifyOtpRequest): BaseResponse
    suspend fun forgotPasswordSavePassword(forgotPasswordSavePasswordRequest: ForgotPasswordSavePasswordRequest): BaseResponse
    suspend fun getDeviceId(
        device_type: String, device_token: String, device_model: String
    ): GetDeviceIdResponse

    suspend fun checkEmailExist(
        email: RequestBody,
        guard: RequestBody
    ): BaseResponse

    suspend fun verifyPhoneNumberOtp(
        phone_number: RequestBody,
        access_code: RequestBody
    ): UserResponse

    suspend fun resendPhoneNumberOtp(
        phone_number: RequestBody
    ): BaseResponse

    suspend fun getRegisterConfiguration(): RegisterConfigurationResponse

    suspend fun storeProfileImage(
        image: MultipartBody.Part?,
    ): StoreProfilePhotoResponse
}