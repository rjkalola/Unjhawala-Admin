package com.unjhawalateaadmin.authentication.data.remote

import com.unjhawalateaadmin.authentication.data.model.*
import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.data.model.RegisterConfigurationResponse
import com.unjhawalateaadmin.dashboard.data.model.MemberDetailsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface AuthenticationInterface {
    /* @POST("kk-login")
     suspend fun login(@Body loginRequest: LoginRequest): UserResponse*/

    @Multipart
    @POST("kk-login")
    suspend fun login(@Part("mobile_number") mobile_number: RequestBody): UserResponse

    @Multipart
    @POST("kk-login")
    suspend fun resendOTP(@Part("mobile_number") mobile_number: RequestBody): UserResponse

    @Multipart
    @POST("kk-validate-otp")
    suspend fun validateOtp(
        @Part("user_id") user_id: RequestBody, @Part("otp") otp: RequestBody, @Part("device_id") device_id: RequestBody
    ): UserResponse

    @Multipart
    @POST("set-login-pin")
    suspend fun setLoginPin(
        @Part("user_id") user_id: RequestBody,
        @Part("mpin") mpin: RequestBody,
    ): UserResponse

    @Multipart
    @POST("quick-login")
    suspend fun quickLogin(
        @Part("user_id") user_id: RequestBody,
        @Part("mpin") mpin: RequestBody,
    ): UserResponse

    @Multipart
    @POST("reset-login-pin")
    suspend fun resetLoginPin(
        @Part("user_id") user_id: RequestBody,
    ): BaseResponse

    @Multipart
    @POST("store-device-token")
    suspend fun storeDeviceToken(
        @Part("device_token") device_token: RequestBody,
    ): BaseResponse

    @POST("kk-reg-user")
    suspend fun register(@Body signUpRequest: SignUpRequest): BaseResponse

    @GET("get-phone-extension")
    suspend fun getPhoneExtension(@Query("country_name") country_name: String): PhoneExtensionResponse

    @Multipart
    @POST("kk-verify-number")
    suspend fun verifyPhoneNumberOtp(
        @Part("phone_number") phone_number: RequestBody,
        @Part("access_code") access_code: RequestBody
    ): UserResponse

    @Multipart
    @POST("kk-resend-code")
    suspend fun resendPhoneNumberOtp(
        @Part("phone_number") phone_number: RequestBody
    ): BaseResponse

    @GET("get-phone-extension")
    suspend fun getCountries(): CountryResponse

    @GET("get-customer")
    suspend fun getCustomerDetails(@Query("customer_id") customer_id: Int): UserResponse

//    @Multipart
//    @POST("edit-profile")
//    suspend fun editProfile(
//        @Body user: User,
//        @Part image: MultipartBody.Part?
//    ): UserResponse


    @Multipart
    @POST("store-profile")
    suspend fun editProfile(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("address") address: RequestBody,
        @Part("email") email: RequestBody,
        @Part("company_name") company_name: RequestBody,
        @Part("state_id") state_id: RequestBody,
        @Part("city") city: RequestBody,
    ): UserResponse

    @POST("change-password")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): BaseResponse

    @Multipart
    @POST("forgot-password/user-exist")
    suspend fun forgotPasswordUserExist(
        @Part("email") email: RequestBody, @Part("guard") guard: RequestBody
    ): ForgotPasswordUserExistResponse

    @POST("forgot-password/send-otp")
    suspend fun forgotPasswordSendOtp(@Body forgotPasswordSendOtpRequest: ForgotPasswordSendOtpRequest): BaseResponse

    @POST("forgot-password/verify-otp")
    suspend fun forgotPasswordVerifyOtp(@Body forgotPasswordVerifyOtpRequest: ForgotPasswordVerifyOtpRequest): BaseResponse

    @POST("forgot-password/save-password")
    suspend fun forgotPasswordSavePassword(@Body forgotPasswordSavePasswordRequest: ForgotPasswordSavePasswordRequest): BaseResponse

    @GET("get-device-id")
    suspend fun getDeviceId(
        @Query("device_type") device_type: String,
        @Query("device_token") device_token: String,
        @Query("device_model") device_model: String
    ): GetDeviceIdResponse

    @Multipart
    @POST("email-check")
    suspend fun checkEmailExist(
        @Part("email") email: RequestBody, @Part("guard") guard: RequestBody
    ): BaseResponse

    @GET("kk-reg-configuration")
    suspend fun getRegisterConfiguration(): RegisterConfigurationResponse

    @Multipart
    @POST("profile/store-image")
    suspend fun storeProfileImage(
        @Part image: MultipartBody.Part?,
    ): StoreProfilePhotoResponse

}

