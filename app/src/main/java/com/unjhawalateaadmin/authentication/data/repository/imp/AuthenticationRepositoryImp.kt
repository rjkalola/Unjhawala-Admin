package com.unjhawalateaadmin.authentication.data.repository.imp

import com.unjhawalateaadmin.authentication.data.model.*
import com.unjhawalateaadmin.authentication.data.remote.AuthenticationInterface
import com.unjhawalateaadmin.authentication.data.repository.AuthenticationRepository
import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.data.model.RegisterConfigurationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AuthenticationRepositoryImp(
    private val authenticationInterface: AuthenticationInterface
) : AuthenticationRepository {
    /*override suspend fun login(loginRequest: LoginRequest): UserResponse {
        return authenticationInterface.login(loginRequest);
    }*/

    override suspend fun login(mobile_number: RequestBody): UserResponse {
        return authenticationInterface.login(mobile_number)
    }

    override suspend fun resendOTP(mobile_number: RequestBody): UserResponse {
        return authenticationInterface.resendOTP(mobile_number)
    }

    override suspend fun validateOtp(user_id: RequestBody, otp: RequestBody): UserResponse {
        return authenticationInterface.validateOtp(user_id, otp)
    }

    override suspend fun setLoginPin(
        user_id: RequestBody,
        mpin: RequestBody,
    ): UserResponse {
        return authenticationInterface.setLoginPin(user_id, mpin);
    }

    override suspend fun quickLogin(
        user_id: RequestBody,
        mpin: RequestBody,
    ): UserResponse {
        return authenticationInterface.quickLogin(user_id, mpin);
    }

    override suspend fun resetLoginPin(user_id: RequestBody): BaseResponse {
        return authenticationInterface.resetLoginPin(user_id);
    }

    override suspend fun storeDeviceToken(device_token: RequestBody): BaseResponse {
        return authenticationInterface.storeDeviceToken(device_token);
    }

    override suspend fun register(signUpRequest: SignUpRequest): BaseResponse {
        return authenticationInterface.register(signUpRequest);
    }

    override suspend fun getPhoneExtension(countryCode: String): PhoneExtensionResponse {
        return authenticationInterface.getPhoneExtension(countryCode)
    }

    override suspend fun getCountries(): CountryResponse {
        return authenticationInterface.getCountries()
    }

    override suspend fun getCustomerDetails(customerId: Int): UserResponse {
        return authenticationInterface.getCustomerDetails(customerId)
    }

    override suspend fun editProfile(
        image: MultipartBody.Part?,
        name: RequestBody,
        address: RequestBody,
        email: RequestBody,
        company_name: RequestBody,
        state_id: RequestBody,
        city: RequestBody,
    ): UserResponse {
        return authenticationInterface.editProfile(
            image,
            name,
            address,
            email,
            company_name,
            state_id,
            city,
        )
    }

    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): BaseResponse {
        return authenticationInterface.changePassword(changePasswordRequest)
    }

    override suspend fun forgotPasswordUserExist(
        email: RequestBody,
        guard: RequestBody
    ): ForgotPasswordUserExistResponse {
        return authenticationInterface.forgotPasswordUserExist(email, guard)
    }

    override suspend fun forgotPasswordSendOtp(forgotPasswordSendOtpRequest: ForgotPasswordSendOtpRequest): BaseResponse {
        return authenticationInterface.forgotPasswordSendOtp(forgotPasswordSendOtpRequest)
    }

    override suspend fun forgotPasswordVerifyOtp(forgotPasswordSendOtpRequest: ForgotPasswordVerifyOtpRequest): BaseResponse {
        return authenticationInterface.forgotPasswordVerifyOtp(forgotPasswordSendOtpRequest)
    }

    override suspend fun forgotPasswordSavePassword(forgotPasswordSavePasswordRequest: ForgotPasswordSavePasswordRequest): BaseResponse {
        return authenticationInterface.forgotPasswordSavePassword(forgotPasswordSavePasswordRequest)
    }

    override suspend fun getDeviceId(
        device_type: String,
        device_token: String,
        device_model: String
    ): GetDeviceIdResponse {
        return authenticationInterface.getDeviceId(device_type, device_token, device_model)
    }

    override suspend fun checkEmailExist(email: RequestBody, guard: RequestBody): BaseResponse {
        return authenticationInterface.checkEmailExist(email, guard)
    }

    override suspend fun verifyPhoneNumberOtp(
        phone_number: RequestBody,
        access_code: RequestBody
    ): UserResponse {
        return authenticationInterface.verifyPhoneNumberOtp(phone_number, access_code)
    }

    override suspend fun resendPhoneNumberOtp(phone_number: RequestBody): BaseResponse {
        return authenticationInterface.resendPhoneNumberOtp(phone_number)
    }

    override suspend fun getRegisterConfiguration(): RegisterConfigurationResponse {
        return authenticationInterface.getRegisterConfiguration()
    }

    override suspend fun storeProfileImage(image: MultipartBody.Part?): StoreProfilePhotoResponse {
        return authenticationInterface.storeProfileImage(image)
    }
}
