package com.unjhawalateaadmin.dashboard.data.remote

import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.dashboard.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Multipart
    @POST("type/garden-area")
    suspend fun getGardenAreaList(
        @Part("limit") limit: RequestBody,
        @Part("offset") offset: RequestBody,
        @Part("search") search: RequestBody
    ): ConfigurationItemListResponse

    @GET("type/garden-area")
    suspend fun getGardenAreaListAll(
    ): ConfigurationItemListResponse

    @Multipart
    @POST("type/garden-area/store")
    suspend fun storeGardenAreaItem(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("status") status: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/garden-area/delete")
    suspend fun deleteGardenAreaItem(
        @Part("id") id: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/garden-area/store-position")
    suspend fun storeGardenAreaPosition(
        @Part("data") data: RequestBody,
    ): BaseResponse

    @Multipart
    @POST("type/leaf-type")
    suspend fun getLeafTypeList(
        @Part("limit") limit: RequestBody,
        @Part("offset") offset: RequestBody,
        @Part("search") search: RequestBody
    ): ConfigurationItemListResponse

    @GET("type/leaf-type")
    suspend fun getLeafTypeListAll(
    ): ConfigurationItemListResponse

    @Multipart
    @POST("type/leaf-type/store")
    suspend fun storeLeafTypeItem(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("status") status: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/leaf-type/delete")
    suspend fun deleteLeafTypeItem(
        @Part("id") id: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/leaf-type/store-position")
    suspend fun storeLeafTypePosition(
        @Part("data") data: RequestBody,
    ): BaseResponse

    @Multipart
    @POST("type/tea-grade")
    suspend fun getTeaGradeList(
        @Part("limit") limit: RequestBody,
        @Part("offset") offset: RequestBody,
        @Part("search") search: RequestBody
    ): ConfigurationItemListResponse

    @GET("type/tea-grade")
    suspend fun getTeaGradeListAll(
    ): ConfigurationItemListResponse

    @Multipart
    @POST("type/tea-grade/store")
    suspend fun storeTeaGradeItem(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("status") status: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/tea-grade/delete")
    suspend fun deleteTeaGradeItem(
        @Part("id") id: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/tea-grade/store-position")
    suspend fun storeTeaGradePosition(
        @Part("data") data: RequestBody,
    ): BaseResponse

    @Multipart
    @POST("type/tea-quality")
    suspend fun getTeaQualityList(
        @Part("limit") limit: RequestBody,
        @Part("offset") offset: RequestBody,
        @Part("search") search: RequestBody
    ): ConfigurationItemListResponse

    @GET("type/tea-quality")
    suspend fun getTeaQualityListAll(
    ): ConfigurationItemListResponse

    @Multipart
    @POST("type/tea-quality/store")
    suspend fun storeTeaQualityItem(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("status") status: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/tea-quality/delete")
    suspend fun deleteTeaQualityItem(
        @Part("id") id: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/tea-quality/store-position")
    suspend fun storeTeaQualityPosition(
        @Part("data") data: RequestBody,
    ): BaseResponse

    @Multipart
    @POST("type/tea-cutting")
    suspend fun getTeaCuttingList(
        @Part("limit") limit: RequestBody,
        @Part("offset") offset: RequestBody,
        @Part("search") search: RequestBody
    ): ConfigurationItemListResponse

    @GET("type/tea-cutting")
    suspend fun getTeaCuttingListAll(
    ): ConfigurationItemListResponse

    @Multipart
    @POST("type/tea-cutting/store")
    suspend fun storeTeaCuttingItem(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("status") status: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/tea-cutting/delete")
    suspend fun deleteTeaCuttingItem(
        @Part("id") id: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/tea-cutting/store-position")
    suspend fun storeTeaCuttingPosition(
        @Part("data") data: RequestBody,
    ): BaseResponse

    @Multipart
    @POST("type/tea-colour")
    suspend fun getTeaColourList(
        @Part("limit") limit: RequestBody,
        @Part("offset") offset: RequestBody,
        @Part("search") search: RequestBody
    ): ConfigurationItemListResponse

    @GET("type/tea-colour")
    suspend fun getTeaColourListAll(
    ): ConfigurationItemListResponse

    @Multipart
    @POST("type/tea-colour/store")
    suspend fun storeTeaColourItem(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("status") status: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/tea-colour/delete")
    suspend fun deleteTeaColourItem(
        @Part("id") id: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/tea-colour/store-position")
    suspend fun storeTeaColourPosition(
        @Part("data") data: RequestBody,
    ): BaseResponse

    @Multipart
    @POST("type/tea-density")
    suspend fun getTeaDensityList(
        @Part("limit") limit: RequestBody,
        @Part("offset") offset: RequestBody,
        @Part("search") search: RequestBody
    ): ConfigurationItemListResponse

    @GET("type/tea-density")
    suspend fun getTeaDensityListAll(
    ): ConfigurationItemListResponse

    @Multipart
    @POST("type/tea-density/store")
    suspend fun storeTeaDensityItem(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("status") status: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/tea-density/delete")
    suspend fun deleteTeaDensityItem(
        @Part("id") id: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/tea-density/store-position")
    suspend fun storeTeaDensityPosition(
        @Part("data") data: RequestBody,
    ): BaseResponse

    @Multipart
    @POST("type/tea-product-preference")
    suspend fun getProductPreferenceList(
        @Part("limit") limit: RequestBody,
        @Part("offset") offset: RequestBody,
        @Part("search") search: RequestBody
    ): ConfigurationItemListResponse

    @GET("type/tea-product-preference")
    suspend fun getProductPreferenceListAll(
    ): ConfigurationItemListResponse

    @Multipart
    @POST("type/tea-product-preference/store")
    suspend fun storeProductPreferenceItem(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("status") status: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/tea-product-preference/delete")
    suspend fun deleteProductPreferenceItem(
        @Part("id") id: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/tea-product-preference/store-position")
    suspend fun storeProductPreferencePosition(
        @Part("data") data: RequestBody,
    ): BaseResponse

    @Multipart
    @POST("type/tea-inward-bag-type")
    suspend fun getTeaInwardBagTypeList(
        @Part("limit") limit: RequestBody,
        @Part("offset") offset: RequestBody,
        @Part("search") search: RequestBody
    ): ConfigurationItemListResponse

    @GET("type/tea-inward-bag-type")
    suspend fun getTeaInwardBagTypeListAll(
    ): ConfigurationItemListResponse

    @Multipart
    @POST("type/tea-inward-bag-type/store")
    suspend fun storeTeaInwardBagTypeItem(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("status") status: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/tea-inward-bag-type/delete")
    suspend fun deleteTeaInwardBagTypeItem(
        @Part("id") id: RequestBody
    ): BaseResponse

    @Multipart
    @POST("type/tea-inward-bag-type/store-position")
    suspend fun storeTeaInwardBagTypePosition(
        @Part("data") data: RequestBody,
    ): BaseResponse

    @GET("tea-gardens/configuration")
    suspend fun getTeaGardenConfiguration(
    ): TeaGardenConfigurationResponse

    @Multipart
    @POST("tea-gardens/lists")
    suspend fun getTeaGardenList(
        @Part("limit") limit: RequestBody,
        @Part("offset") offset: RequestBody,
        @Part("search") search: RequestBody
    ): ConfigurationItemListResponse

    @GET("tea-gardens/records")
    suspend fun getTeaGardenListAll(
    ): ConfigurationItemListResponse

    @Multipart
    @POST("tea-garden/store")
    suspend fun storeTeaGarden(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("status") status: RequestBody,
        @Part("lu_leaf_type_id") lu_leaf_type_id: RequestBody,
        @Part("lu_garden_area_id") lu_garden_area_id: RequestBody,
    ): BaseResponse

    @Multipart
    @POST("tea-garden/delete")
    suspend fun deleteTeaGarden(
        @Part("id") id: RequestBody
    ): BaseResponse

    @Multipart
    @POST("tea-gardens/store-position")
    suspend fun storeTeaGardensPosition(
        @Part("data") data: RequestBody,
    ): BaseResponse

    //Tea Season APIs
    @GET("tea-seasons/configuration")
    suspend fun getTeaSeasonConfiguration(
    ): TeaSeasonConfigurationResponse

    @Multipart
    @POST("tea-seasons/lists")
    suspend fun getTeaSeasonList(
        @Part("limit") limit: RequestBody,
        @Part("offset") offset: RequestBody,
        @Part("search") search: RequestBody
    ): ConfigurationItemListResponse

    @GET("tea-seasons/records")
    suspend fun getTeaSeasonListAll(
    ): ConfigurationItemListResponse

    @POST("tea-season/store")
    suspend fun storeTeaSeason(@Body configurationItemInfo: ConfigurationItemInfo): BaseResponse

    @Multipart
    @POST("tea-season/delete")
    suspend fun deleteTeaSeason(
        @Part("id") id: RequestBody
    ): BaseResponse

    @Multipart
    @POST("tea-seasons/store-position")
    suspend fun storeTeaSeasonsPosition(
        @Part("data") data: RequestBody,
    ): BaseResponse

    //Tea Sample
    @Multipart
    @POST("tea-samples/lists")
    suspend fun getTeaSampleList(
        @Part("limit") limit: RequestBody,
        @Part("offset") offset: RequestBody,
        @Part("search") search: RequestBody,
        @Part("filters") filters: RequestBody,
        @Part("start_date") start_date: RequestBody,
        @Part("end_date") end_date: RequestBody,
    ): TeaSampleListResponse

    @GET("tea-samples/configuration")
    suspend fun getTeaSampleConfiguration(
    ): TeaSampleConfigurationResponse

    @POST("tea-sample/store")
    suspend fun storeTeaSample(@Body teaSampleInfo: TeaSampleInfo): BaseResponse

    @Multipart
    @POST("tea-sample/store-testing")
    suspend fun storeTeaSampleTesting(
        @Part("id") id: RequestBody,
        @Part("lu_tea_personal_grade_id") lu_tea_personal_grade_id: RequestBody,
        @Part("lu_tea_cutting_id") lu_tea_cutting_id: RequestBody,
        @Part("lu_tea_colour_id") lu_tea_colour_id: RequestBody,
        @Part("lu_tea_density_id") lu_tea_density_id: RequestBody,
        @Part("lu_tea_source_level_1_id") lu_tea_source_level_1_id: RequestBody,
        @Part("lu_tea_source_level_2_id") lu_tea_source_level_2_id: RequestBody,
        @Part("lu_tea_source_level_3_id") lu_tea_source_level_3_id: RequestBody,
        @Part("lu_tea_season_id") lu_tea_season_id: RequestBody,
        @Part("quality_id") quality_id: RequestBody,
        @Part("lu_tea_product_preference_id") lu_tea_product_preference_id: RequestBody,
        @Part("manufacturer_date") manufacturer_date: RequestBody,
        @Part("note") note: RequestBody,
        @Part("rating") rating: RequestBody,
        @Part image: MultipartBody.Part?,
    ): BaseResponse

    @Multipart
    @POST("tea-confirmation/available-tea-sample")
    suspend fun availableTeaSampleList(
        @Part("search") search: RequestBody,
        @Part("start_date") start_date: RequestBody,
        @Part("end_date") end_date: RequestBody,
    ): AvailableTeaSampleListResponse

    @GET("tea-confirmation/configuration")
    suspend fun getAvailableTeaSampleConfiguration(
    ): AvailableTeaSampleConfigurationResponse

    @Multipart
    @POST("tea-confirmation/store")
    suspend fun storeTeaConfirmation(
        @Part("vendor_id") vendor_id: RequestBody,
        @Part("created_date") created_date: RequestBody,
        @Part("records") records: RequestBody,
        @Part file: MultipartBody.Part?,
    ): BaseResponse

    @Multipart
    @POST("tea-confirmation/lists")
    suspend fun getTeaConfirmationList(
        @Part("limit") limit: RequestBody,
        @Part("offset") offset: RequestBody,
        @Part("search") search: RequestBody
    ): AvailableTeaSampleListResponse

    @POST("tea-confirmation/grade-list")
    suspend fun getTeaConfirmationGradeList(
    ): AvailableTeaSampleListResponse

    @GET("tea-sources/configuration")
    suspend fun getTeaSourcesConfiguration(
    ): TeaSourceLevelListResponse

    @POST("tea-source/store")
    suspend fun storeTeaSource(@Body teaSourceLevelInfo: TeaSourceLevelInfo): BaseResponse

    @Multipart
    @POST("tea-source/delete")
    suspend fun deleteTeaSource(
        @Part("id") limit: RequestBody,
    ): BaseResponse

    @Multipart
    @POST("tea-testing-data/lists")
    suspend fun getTeaTestedDataList(
        @Part("limit") limit: RequestBody,
        @Part("offset") offset: RequestBody,
        @Part("search") search: RequestBody,
        @Part("filters") filters: RequestBody,
        @Part("start_date") start_date: RequestBody,
        @Part("end_date") end_date: RequestBody,
    ): TeaTestedSampleListResponse

    @Multipart
    @POST("tea-testing-data/details")
    suspend fun teaTestingDataDetails(
        @Part("id") id: RequestBody,
    ): TeaTestedSampleDetailsResponse

    @Multipart
    @POST("tea-testing-data/store-testing")
    suspend fun storeTeaTestedSample(
        @Part("id") id: RequestBody,
        @Part("lu_tea_personal_grade_id") lu_tea_personal_grade_id: RequestBody,
        @Part("lu_tea_cutting_id") lu_tea_cutting_id: RequestBody,
        @Part("lu_tea_colour_id") lu_tea_colour_id: RequestBody,
        @Part("lu_tea_density_id") lu_tea_density_id: RequestBody,
        @Part("lu_tea_source_level_1_id") lu_tea_source_level_1_id: RequestBody,
        @Part("lu_tea_source_level_2_id") lu_tea_source_level_2_id: RequestBody,
        @Part("lu_tea_source_level_3_id") lu_tea_source_level_3_id: RequestBody,
        @Part("lu_tea_season_id") lu_tea_season_id: RequestBody,
        @Part("quality_id") quality_id: RequestBody,
        @Part("lu_tea_product_preference_id") lu_tea_product_preference_id: RequestBody,
        @Part("manufacturer_date") manufacturer_date: RequestBody,
        @Part("note") note: RequestBody,
        @Part("rating") rating: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("vendor_id") vendor_id: RequestBody,
        @Part("lu_garden_id") lu_garden_id: RequestBody,
        @Part("lu_tea_grade_id") lu_tea_grade_id: RequestBody,
        @Part("bag") bag: RequestBody,
        @Part("weight") weight: RequestBody,
        @Part("rate") rate: RequestBody,
    ): BaseResponse
}

