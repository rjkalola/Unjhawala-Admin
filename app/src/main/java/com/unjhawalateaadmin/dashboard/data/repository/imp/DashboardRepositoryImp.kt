package com.unjhawalateaadmin.dashboard.data.repository.imp

import com.unjhawalateaadmin.dashboard.data.model.*
import com.unjhawalateaadmin.dashboard.data.remote.DashboardInterface
import com.unjhawalateaadmin.dashboard.data.repository.DashboardRepository

class DashboardRepositoryImp(
    private val dashboardInterface: DashboardInterface
) : DashboardRepository {

    override suspend fun getDashboard(): DashboardResponse {
        return dashboardInterface.getDashboard()
    }

    override suspend fun getTermsConditions(): PrivacyPolicyResponse {
        return dashboardInterface.getTermsConditions()
    }

    override suspend fun getPrivacyPolicy(): PrivacyPolicyResponse {
        return dashboardInterface.getPrivacyPolicy()
    }



}
