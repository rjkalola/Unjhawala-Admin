package com.unjhawalateaadmin.dashboard.data.repository

import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.dashboard.data.model.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DashboardRepository {
    suspend fun getDashboard(
    ): DashboardResponse

    suspend fun getTermsConditions(
    ): PrivacyPolicyResponse

    suspend fun getPrivacyPolicy(
    ): PrivacyPolicyResponse

    suspend fun getGardenAreaList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse

    suspend fun getGardenAreaListAll(
    ): ConfigurationItemListResponse

    suspend fun storeGardenAreaItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse

    suspend fun deleteGardenAreaItem(
        id: RequestBody
    ): BaseResponse

    suspend fun storeGardenAreaPosition(
        data: RequestBody,
    ): BaseResponse

    suspend fun getLeafTypeList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse

    suspend fun getLeafTypeListAll(
    ): ConfigurationItemListResponse

    suspend fun storeLeafTypeItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse

    suspend fun deleteLeafTypeItem(
        id: RequestBody
    ): BaseResponse

    suspend fun storeLeafTypePosition(
        data: RequestBody,
    ): BaseResponse

    suspend fun getTeaGradeList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse

    suspend fun getTeaGradeListAll(
    ): ConfigurationItemListResponse

    suspend fun storeTeaGradeItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse


    suspend fun deleteTeaGradeItem(
        id: RequestBody
    ): BaseResponse

    suspend fun storeTeaGradePosition(
        data: RequestBody,
    ): BaseResponse

    suspend fun getTeaQualityList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse

    suspend fun getTeaQualityListAll(
    ): ConfigurationItemListResponse

    suspend fun storeTeaQualityItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse


    suspend fun deleteTeaQualityItem(
        id: RequestBody
    ): BaseResponse

    suspend fun storeTeaQualityPosition(
        data: RequestBody,
    ): BaseResponse

    suspend fun getTeaCuttingList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse

    suspend fun getTeaCuttingListAll(
    ): ConfigurationItemListResponse

    suspend fun storeTeaCuttingItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse


    suspend fun deleteTeaCuttingItem(
        id: RequestBody
    ): BaseResponse

    suspend fun storeTeaCuttingPosition(
        data: RequestBody,
    ): BaseResponse

    suspend fun getTeaColourList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse

    suspend fun getTeaColourListAll(
    ): ConfigurationItemListResponse

    suspend fun storeTeaColourItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse

    suspend fun deleteTeaColourItem(
        id: RequestBody
    ): BaseResponse

    suspend fun storeTeaColourPosition(
        data: RequestBody,
    ): BaseResponse

    suspend fun getTeaDensityList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse

    suspend fun getTeaDensityListAll(
    ): ConfigurationItemListResponse

    suspend fun storeTeaDensityItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse

    suspend fun deleteTeaDensityItem(
        id: RequestBody
    ): BaseResponse

    suspend fun storeTeaDensityPosition(
        data: RequestBody,
    ): BaseResponse

    suspend fun getProductPreferenceList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse

    suspend fun getProductPreferenceListAll(
    ): ConfigurationItemListResponse

    suspend fun storeProductPreferenceItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse

    suspend fun deleteProductPreferenceItem(
        id: RequestBody
    ): BaseResponse

    suspend fun storeProductPreferencePosition(
        data: RequestBody,
    ): BaseResponse

    suspend fun getTeaInwardBagTypeList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse

    suspend fun getTeaInwardBagTypeListAll(
    ): ConfigurationItemListResponse

    suspend fun storeTeaInwardBagTypeItem(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody
    ): BaseResponse

    suspend fun deleteTeaInwardBagTypeItem(
        id: RequestBody
    ): BaseResponse

    suspend fun storeTeaInwardBagTypePosition(
        data: RequestBody,
    ): BaseResponse

    suspend fun getTeaGardenConfiguration(
    ): TeaGardenConfigurationResponse

    suspend fun getTeaGardenList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse

    suspend fun getTeaGardenListAll(
    ): ConfigurationItemListResponse

    suspend fun storeTeaGarden(
        id: RequestBody,
        name: RequestBody,
        status: RequestBody,
        lu_leaf_type_id: RequestBody,
        lu_garden_area_id: RequestBody,
    ): BaseResponse

    suspend fun deleteTeaGarden(
        id: RequestBody
    ): BaseResponse

    suspend fun storeTeaGardensPosition(
        data: RequestBody,
    ): BaseResponse


    suspend fun getTeaSeasonConfiguration(
    ): TeaSeasonConfigurationResponse

    suspend fun getTeaSeasonList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): ConfigurationItemListResponse

    suspend fun getTeaSeasonListAll(
    ): ConfigurationItemListResponse

    suspend fun storeTeaSeason(configurationItemInfo: ConfigurationItemInfo): BaseResponse

    suspend fun deleteTeaSeason(
        id: RequestBody
    ): BaseResponse

    suspend fun storeTeaSeasonsPosition(
        data: RequestBody,
    ): BaseResponse

    suspend fun getTeaSampleList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody
    ): TeaSampleListResponse
}