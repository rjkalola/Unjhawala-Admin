package com.unjhawalateaadmin.dashboard.data.remote

import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.dashboard.data.model.*
import retrofit2.http.*

interface DashboardInterface {
    @GET("dashboard")
    suspend fun getDashboard(
    ): DashboardResponse

    @GET("terms-conditions")
    suspend fun getTermsConditions(
    ): PrivacyPolicyResponse

    @GET("privacy-policy")
    suspend fun getPrivacyPolicy(
    ): PrivacyPolicyResponse

    @GET("accept-terms")
    suspend fun acceptTermsConditions(
    ): BaseResponse

}

