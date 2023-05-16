package com.unjhawalateaadmin.dashboard.data.repository.imp

import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.dashboard.data.model.*
import com.unjhawalateaadmin.dashboard.data.remote.DashboardInterface
import com.unjhawalateaadmin.dashboard.data.repository.DashboardRepository
import okhttp3.RequestBody

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

    override suspend fun getGardenAreaList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse {
        return dashboardInterface.getGardenAreaList(limit, offset, search)
    }

    override suspend fun getGardenAreaListAll(): ConfigurationItemListResponse {
        return dashboardInterface.getGardenAreaListAll()
    }

    override suspend fun storeGardenAreaItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse {
        return dashboardInterface.storeGardenAreaItem(id, name, status)
    }

    override suspend fun deleteGardenAreaItem(id: RequestBody): BaseResponse {
        return dashboardInterface.deleteGardenAreaItem(id)
    }

    override suspend fun storeGardenAreaPosition(data: RequestBody): BaseResponse {
        return dashboardInterface.storeGardenAreaPosition(data)
    }

    override suspend fun getLeafTypeList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse {
        return dashboardInterface.getLeafTypeList(limit, offset, search)
    }

    override suspend fun getLeafTypeListAll(): ConfigurationItemListResponse {
        return dashboardInterface.getLeafTypeListAll()
    }

    override suspend fun storeLeafTypeItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse {
        return dashboardInterface.storeLeafTypeItem(id, name, status)
    }

    override suspend fun deleteLeafTypeItem(id: RequestBody): BaseResponse {
        return dashboardInterface.deleteLeafTypeItem(id)
    }

    override suspend fun storeLeafTypePosition(data: RequestBody): BaseResponse {
        return dashboardInterface.storeLeafTypePosition(data)
    }

    override suspend fun getTeaGradeList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse {
        return dashboardInterface.getTeaGradeList(limit, offset, search)
    }

    override suspend fun getTeaGradeListAll(): ConfigurationItemListResponse {
        return dashboardInterface.getTeaGradeListAll()
    }

    override suspend fun storeTeaGradeItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse {
        return dashboardInterface.storeTeaGradeItem(id, name, status)
    }

    override suspend fun deleteTeaGradeItem(id: RequestBody): BaseResponse {
        return dashboardInterface.deleteTeaGradeItem(id)
    }

    override suspend fun storeTeaGradePosition(data: RequestBody): BaseResponse {
        return dashboardInterface.storeTeaGradePosition(data)
    }

    override suspend fun getTeaQualityList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse {
        return dashboardInterface.getTeaQualityList(limit, offset, search)
    }

    override suspend fun getTeaQualityListAll(): ConfigurationItemListResponse {
        return dashboardInterface.getTeaQualityListAll()
    }

    override suspend fun storeTeaQualityItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse {
        return dashboardInterface.storeTeaQualityItem(id, name, status)
    }

    override suspend fun deleteTeaQualityItem(id: RequestBody): BaseResponse {
        return dashboardInterface.deleteTeaQualityItem(id)
    }

    override suspend fun storeTeaQualityPosition(data: RequestBody): BaseResponse {
        return dashboardInterface.storeTeaQualityPosition(data)
    }

    override suspend fun getTeaCuttingList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse {
        return dashboardInterface.getTeaCuttingList(limit, offset, search)
    }

    override suspend fun getTeaCuttingListAll(): ConfigurationItemListResponse {
        return dashboardInterface.getTeaCuttingListAll()
    }

    override suspend fun storeTeaCuttingItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse {
        return dashboardInterface.storeTeaCuttingItem(id, name, status)
    }

    override suspend fun deleteTeaCuttingItem(id: RequestBody): BaseResponse {
        return dashboardInterface.deleteTeaCuttingItem(id)
    }

    override suspend fun storeTeaCuttingPosition(data: RequestBody): BaseResponse {
        return dashboardInterface.storeTeaCuttingPosition(data)
    }

    override suspend fun getTeaColourList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse {
        return dashboardInterface.getTeaColourList(limit, offset, search)
    }

    override suspend fun getTeaColourListAll(): ConfigurationItemListResponse {
        return dashboardInterface.getTeaColourListAll()
    }

    override suspend fun storeTeaColourItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse {
        return dashboardInterface.storeTeaColourItem(id, name, status)
    }

    override suspend fun deleteTeaColourItem(id: RequestBody): BaseResponse {
        return dashboardInterface.deleteTeaColourItem(id)
    }

    override suspend fun storeTeaColourPosition(data: RequestBody): BaseResponse {
        return dashboardInterface.storeTeaColourPosition(data)
    }

    override suspend fun getTeaDensityList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse {
        return dashboardInterface.getTeaDensityList(limit, offset, search)
    }

    override suspend fun getTeaDensityListAll(): ConfigurationItemListResponse {
        return dashboardInterface.getTeaDensityListAll()
    }

    override suspend fun storeTeaDensityItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse {
        return dashboardInterface.storeTeaDensityItem(id, name, status)
    }

    override suspend fun deleteTeaDensityItem(id: RequestBody): BaseResponse {
        return dashboardInterface.deleteTeaDensityItem(id)
    }

    override suspend fun storeTeaDensityPosition(data: RequestBody): BaseResponse {
        return dashboardInterface.storeTeaDensityPosition(data)
    }

    override suspend fun getProductPreferenceList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse {
        return dashboardInterface.getProductPreferenceList(limit, offset, search)
    }

    override suspend fun getProductPreferenceListAll(): ConfigurationItemListResponse {
        return dashboardInterface.getProductPreferenceListAll()
    }

    override suspend fun storeProductPreferenceItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse {
        return dashboardInterface.storeProductPreferenceItem(id, name, status)
    }

    override suspend fun deleteProductPreferenceItem(id: RequestBody): BaseResponse {
        return dashboardInterface.deleteProductPreferenceItem(id)
    }

    override suspend fun storeProductPreferencePosition(data: RequestBody): BaseResponse {
        return dashboardInterface.storeProductPreferencePosition(data)
    }

    override suspend fun getTeaInwardBagTypeList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse {
        return dashboardInterface.getTeaInwardBagTypeList(limit, offset, search)
    }

    override suspend fun getTeaInwardBagTypeListAll(): ConfigurationItemListResponse {
        return dashboardInterface.getTeaInwardBagTypeListAll()
    }

    override suspend fun storeTeaInwardBagTypeItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse {
        return dashboardInterface.storeTeaInwardBagTypeItem(id, name, status)
    }

    override suspend fun deleteTeaInwardBagTypeItem(id: RequestBody): BaseResponse {
        return dashboardInterface.deleteTeaInwardBagTypeItem(id)
    }

    override suspend fun storeTeaInwardBagTypePosition(data: RequestBody): BaseResponse {
        return dashboardInterface.storeTeaInwardBagTypePosition(data)
    }

    override suspend fun getTeaGardenConfiguration(): TeaGardenConfigurationResponse {
        return dashboardInterface.getTeaGardenConfiguration()
    }

    override suspend fun getTeaGardenList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse {
        return dashboardInterface.getTeaGardenList(limit, offset, search)
    }

    override suspend fun getTeaGardenListAll(): ConfigurationItemListResponse {
        return dashboardInterface.getTeaGardenListAll()
    }

    override suspend fun storeTeaGarden(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody,
        lu_leaf_type_id: RequestBody,
        lu_garden_area_id: RequestBody
    ): BaseResponse {
        return dashboardInterface.storeTeaGarden(
            id,
            name,
            status,
            lu_leaf_type_id,
            lu_garden_area_id
        )
    }

    override suspend fun deleteTeaGarden(id: RequestBody): BaseResponse {
        return dashboardInterface.deleteTeaGarden(id)
    }

    override suspend fun storeTeaGardensPosition(data: RequestBody): BaseResponse {
        return dashboardInterface.storeTeaGardensPosition(data)
    }

    override suspend fun getTeaSeasonConfiguration(): TeaSeasonConfigurationResponse {
        return dashboardInterface.getTeaSeasonConfiguration()
    }

    override suspend fun getTeaSeasonList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse {
        return dashboardInterface.getTeaSeasonList(limit, offset, search)
    }

    override suspend fun getTeaSeasonListAll(): ConfigurationItemListResponse {
        return dashboardInterface.getTeaSeasonListAll()
    }

    override suspend fun storeTeaSeason(configurationItemInfo: ConfigurationItemInfo): BaseResponse {
        return dashboardInterface.storeTeaSeason(configurationItemInfo)
    }

    override suspend fun deleteTeaSeason(id: RequestBody): BaseResponse {
        return dashboardInterface.deleteTeaSeason(id)
    }

    override suspend fun storeTeaSeasonsPosition(data: RequestBody): BaseResponse {
        return dashboardInterface.storeTeaSeasonsPosition(data)
    }

    override suspend fun getTeaSampleList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): TeaSampleListResponse {
        return dashboardInterface.getTeaSampleList(limit, offset, search)
    }
}
