package com.unjhawalateaadmin.dashboard.data.repository

import com.unjhawalateaadmin.dashboard.data.model.*

interface DashboardRepository {
    suspend fun getDashboard(
    ): DashboardResponse

    suspend fun getTermsConditions(
    ): PrivacyPolicyResponse

    suspend fun getPrivacyPolicy(
    ): PrivacyPolicyResponse

}