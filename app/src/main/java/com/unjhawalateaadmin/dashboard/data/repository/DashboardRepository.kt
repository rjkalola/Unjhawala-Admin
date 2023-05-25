package com.unjhawalateaadmin.dashboard.data.repository

import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.dashboard.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

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
        search: RequestBody,
        filters: RequestBody,
        start_date: RequestBody,
        end_date: RequestBody,
    ): TeaSampleListResponse

    suspend fun getTeaSampleConfiguration(
    ): TeaSampleConfigurationResponse

    suspend fun storeTeaSample(teaSampleInfo: TeaSampleInfo): BaseResponse

    suspend fun storeTeaSampleTesting(
        id: RequestBody,
        lu_tea_personal_grade_id: RequestBody,
        lu_tea_cutting_id: RequestBody,
        lu_tea_colour_id: RequestBody,
        lu_tea_density_id: RequestBody,
        lu_tea_source_level_1_id: RequestBody,
        lu_tea_source_level_2_id: RequestBody,
        lu_tea_source_level_3_id: RequestBody,
        lu_tea_season_detail_id: RequestBody,
        our_quality_id: RequestBody,
        lu_tea_product_preference_id: RequestBody,
        manufacturer_date: RequestBody,
        note: RequestBody,
        rating: RequestBody,
        image: MultipartBody.Part?,
    ): BaseResponse

    suspend fun availableTeaSampleList(
        search: RequestBody,
    ): AvailableTeaSampleListResponse

    suspend fun getAvailableTeaSampleConfiguration(
    ): AvailableTeaSampleConfigurationResponse

    suspend fun storeTeaConfirmation(
        vendor_id: RequestBody,
        created_date: RequestBody,
        records: RequestBody,
        file: MultipartBody.Part?,
    ): BaseResponse

    suspend fun getTeaConfirmationList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody,
    ): AvailableTeaSampleListResponse

    suspend fun getTeaConfirmationGradeList(
    ): AvailableTeaSampleListResponse

    suspend fun getTeaSourcesConfiguration(
    ): TeaSourceLevelListResponse

    suspend fun storeTeaSource(teaSourceLevelInfo: TeaSourceLevelInfo): BaseResponse

    suspend fun deleteTeaSource(
        limit: RequestBody,
    ): BaseResponse

    suspend fun getTeaTestedDataList(
        limit: RequestBody,
        offset: RequestBody,
        search: RequestBody,
    ): TeaTestedSampleListResponse

    suspend fun teaTestingDataDetails(
        id: RequestBody,
    ): TeaTestedSampleDetailsResponse

    suspend fun storeTeaTestedSample(
        id: RequestBody,
        lu_tea_personal_grade_id: RequestBody,
        lu_tea_cutting_id: RequestBody,
        lu_tea_colour_id: RequestBody,
        lu_tea_density_id: RequestBody,
        lu_tea_source_level_1_id: RequestBody,
        lu_tea_source_level_2_id: RequestBody,
        lu_tea_source_level_3_id: RequestBody,
        lu_tea_season_detail_id: RequestBody,
        our_quality_id: RequestBody,
        lu_tea_product_preference_id: RequestBody,
        manufacturer_date: RequestBody,
        note: RequestBody,
        rating: RequestBody,
        image: MultipartBody.Part?,
        vendor_id: RequestBody,
        lu_garden_id: RequestBody,
        lu_tea_grade_id: RequestBody,
        bag: RequestBody,
        weight: RequestBody,
        rate: RequestBody,
    ): BaseResponse
}