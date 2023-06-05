package com.unjhawalateaadmin.dashboard.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imateplus.utilities.utils.StringHelper
import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.common.utils.traceErrorException
import com.unjhawalateaadmin.dashboard.data.model.AvailableTeaSampleConfigurationResponse
import com.unjhawalateaadmin.dashboard.data.model.AvailableTeaSampleListResponse
import com.unjhawalateaadmin.dashboard.data.model.ConfigurationItemInfo
import com.unjhawalateaadmin.dashboard.data.model.ConfigurationItemListResponse
import com.unjhawalateaadmin.dashboard.data.model.DashboardResponse
import com.unjhawalateaadmin.dashboard.data.model.PrivacyPolicyResponse
import com.unjhawalateaadmin.dashboard.data.model.TeaGardenConfigurationResponse
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleConfigurationResponse
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleInfo
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleListResponse
import com.unjhawalateaadmin.dashboard.data.model.TeaSampleTestingInfo
import com.unjhawalateaadmin.dashboard.data.model.TeaSeasonConfigurationResponse
import com.unjhawalateaadmin.dashboard.data.model.TeaSourceLevelInfo
import com.unjhawalateaadmin.dashboard.data.model.TeaSourceLevelListResponse
import com.unjhawalateaadmin.dashboard.data.model.TeaTestedSampleDetailsResponse
import com.unjhawalateaadmin.dashboard.data.model.TeaTestedSampleListResponse
import com.unjhawalateaadmin.dashboard.data.repository.DashboardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import java.io.File
import java.util.concurrent.CancellationException

class DashboardViewModel(private val dashboardRepository: DashboardRepository) :
    ViewModel() {
    val baseResponse = MutableLiveData<BaseResponse>()
    val dashboardResponse = MutableLiveData<DashboardResponse>()
    val privacyPolicyResponse = MutableLiveData<PrivacyPolicyResponse>()
    val acceptTermsConditionsResponse = MutableLiveData<BaseResponse>()
    val mConfigurationItemListResponse = MutableLiveData<ConfigurationItemListResponse>()
    val mConfigurationAllItemListResponse = MutableLiveData<ConfigurationItemListResponse>()
    val storeConfigurationItem = MutableLiveData<BaseResponse>()
    val deleteConfigurationItem = MutableLiveData<BaseResponse>()
    val storeConfigurationItemPosition = MutableLiveData<BaseResponse>()
    val teaGardenConfigurationResponse = MutableLiveData<TeaGardenConfigurationResponse>()
    val teaSeasonConfigurationResponse = MutableLiveData<TeaSeasonConfigurationResponse>()
    val mTeaSampleListResponse = MutableLiveData<TeaSampleListResponse>()
    val teaSampleConfigurationResponse = MutableLiveData<TeaSampleConfigurationResponse>()
    val storeTeaSampleResponse = MutableLiveData<BaseResponse>()
    val storeTeaSampleTestingResponse = MutableLiveData<BaseResponse>()
    val mAvailableTeaSampleListResponse = MutableLiveData<AvailableTeaSampleListResponse>()
    val mAvailableTeaSampleConfigurationResponse =
        MutableLiveData<AvailableTeaSampleConfigurationResponse>()
    val storeAvailableTeaSample = MutableLiveData<BaseResponse>()
    val teaSourceLevelListResponse = MutableLiveData<TeaSourceLevelListResponse>()
    val editDeleteTeaSource = MutableLiveData<BaseResponse>()
    val mTeaTestedSampleListResponse = MutableLiveData<TeaTestedSampleListResponse>()
    val mTeaTestedSampleDetailsResponse = MutableLiveData<TeaTestedSampleDetailsResponse>()

    fun getDashboardResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = dashboardRepository.getDashboard()
                withContext(Dispatchers.Main) {
                    dashboardResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getTermsConditions() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    dashboardRepository.getTermsConditions()
                withContext(Dispatchers.Main) {
                    privacyPolicyResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getPrivacyPolicy() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    dashboardRepository.getPrivacyPolicy()
                withContext(Dispatchers.Main) {
                    privacyPolicyResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getConfigurationItemList(limit: Int, offset: Int, search: String, type: Int) {
        val limitBody: RequestBody = AppUtils.getRequestBody(limit.toString())
        val offsetBody: RequestBody = AppUtils.getRequestBody(offset.toString())
        val searchBody: RequestBody = AppUtils.getRequestBody(search)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: ConfigurationItemListResponse? = null;
                when (type) {
                    AppConstants.TeaConfiguration.TEA_GARDEN_AREA -> response =
                        dashboardRepository.getGardenAreaList(limitBody, offsetBody, searchBody)

                    AppConstants.TeaConfiguration.LEAF_TYPE -> response =
                        dashboardRepository.getLeafTypeList(limitBody, offsetBody, searchBody)

                    AppConstants.TeaConfiguration.TEA_GRADE -> response =
                        dashboardRepository.getTeaGradeList(limitBody, offsetBody, searchBody)

                    AppConstants.TeaConfiguration.TEA_QUALITY -> response =
                        dashboardRepository.getTeaQualityList(limitBody, offsetBody, searchBody)

                    AppConstants.TeaConfiguration.TEA_TYPE -> response =
                        dashboardRepository.getTeaCuttingList(limitBody, offsetBody, searchBody)

                    AppConstants.TeaConfiguration.TEA_COLOR -> response =
                        dashboardRepository.getTeaColourList(limitBody, offsetBody, searchBody)

                    AppConstants.TeaConfiguration.TEA_DENSITY -> response =
                        dashboardRepository.getTeaDensityList(limitBody, offsetBody, searchBody)

                    AppConstants.TeaConfiguration.TEA_PRODUCT_PREFERENCE -> response =
                        dashboardRepository.getProductPreferenceList(
                            limitBody,
                            offsetBody,
                            searchBody
                        )

                    AppConstants.TeaConfiguration.TEA_INWARD_BAG_TYPE -> response =
                        dashboardRepository.getTeaInwardBagTypeList(
                            limitBody,
                            offsetBody,
                            searchBody
                        )

                    AppConstants.TeaConfiguration.TEA_GARDEN -> response =
                        dashboardRepository.getTeaGardenList(
                            limitBody,
                            offsetBody,
                            searchBody
                        )

                    AppConstants.TeaConfiguration.TEA_SEASON -> response =
                        dashboardRepository.getTeaSeasonList(
                            limitBody,
                            offsetBody,
                            searchBody
                        )
                }
                withContext(Dispatchers.Main) {
                    mConfigurationItemListResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getConfigurationAllItemList(type: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: ConfigurationItemListResponse? = null;
                when (type) {
                    AppConstants.TeaConfiguration.TEA_GARDEN_AREA -> response =
                        dashboardRepository.getGardenAreaListAll()

                    AppConstants.TeaConfiguration.LEAF_TYPE -> response =
                        dashboardRepository.getLeafTypeListAll()

                    AppConstants.TeaConfiguration.TEA_GRADE -> response =
                        dashboardRepository.getTeaGradeListAll()

                    AppConstants.TeaConfiguration.TEA_QUALITY -> response =
                        dashboardRepository.getTeaQualityListAll()

                    AppConstants.TeaConfiguration.TEA_TYPE -> response =
                        dashboardRepository.getTeaCuttingListAll()

                    AppConstants.TeaConfiguration.TEA_COLOR -> response =
                        dashboardRepository.getTeaColourListAll()

                    AppConstants.TeaConfiguration.TEA_DENSITY -> response =
                        dashboardRepository.getTeaDensityListAll()

                    AppConstants.TeaConfiguration.TEA_PRODUCT_PREFERENCE -> response =
                        dashboardRepository.getProductPreferenceListAll()

                    AppConstants.TeaConfiguration.TEA_INWARD_BAG_TYPE -> response =
                        dashboardRepository.getTeaInwardBagTypeListAll()

                    AppConstants.TeaConfiguration.TEA_GARDEN -> response =
                        dashboardRepository.getTeaGardenListAll()

                    AppConstants.TeaConfiguration.TEA_SEASON -> response =
                        dashboardRepository.getTeaSeasonListAll()
                }

                withContext(Dispatchers.Main) {
                    mConfigurationAllItemListResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun storeConfigurationItem(id: String, name: String, status: Int, type: Int) {
        val idBody: RequestBody = AppUtils.getRequestBody(id)
        val nameBody: RequestBody = AppUtils.getRequestBody(name)
        val statusBody: RequestBody = AppUtils.getRequestBody(status.toString())

        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: BaseResponse? = null;
                when (type) {
                    AppConstants.TeaConfiguration.TEA_GARDEN_AREA -> response =
                        dashboardRepository.storeGardenAreaItem(idBody, nameBody, statusBody)

                    AppConstants.TeaConfiguration.LEAF_TYPE -> response =
                        dashboardRepository.storeLeafTypeItem(idBody, nameBody, statusBody)

                    AppConstants.TeaConfiguration.TEA_GRADE -> response =
                        dashboardRepository.storeTeaGradeItem(idBody, nameBody, statusBody)

                    AppConstants.TeaConfiguration.TEA_QUALITY -> response =
                        dashboardRepository.storeTeaQualityItem(idBody, nameBody, statusBody)

                    AppConstants.TeaConfiguration.TEA_TYPE -> response =
                        dashboardRepository.storeTeaCuttingItem(idBody, nameBody, statusBody)

                    AppConstants.TeaConfiguration.TEA_COLOR -> response =
                        dashboardRepository.storeTeaColourItem(idBody, nameBody, statusBody)

                    AppConstants.TeaConfiguration.TEA_DENSITY -> response =
                        dashboardRepository.storeTeaDensityItem(idBody, nameBody, statusBody)

                    AppConstants.TeaConfiguration.TEA_PRODUCT_PREFERENCE -> response =
                        dashboardRepository.storeProductPreferenceItem(idBody, nameBody, statusBody)

                    AppConstants.TeaConfiguration.TEA_INWARD_BAG_TYPE -> response =
                        dashboardRepository.storeTeaInwardBagTypeItem(idBody, nameBody, statusBody)
                }
                withContext(Dispatchers.Main) {
                    storeConfigurationItem.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun deleteConfigurationItem(id: String, type: Int) {
        val idBody: RequestBody = AppUtils.getRequestBody(id)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: BaseResponse? = null;
                when (type) {
                    AppConstants.TeaConfiguration.TEA_GARDEN_AREA -> response =
                        dashboardRepository.deleteGardenAreaItem(idBody)

                    AppConstants.TeaConfiguration.LEAF_TYPE -> response =
                        dashboardRepository.deleteLeafTypeItem(idBody)

                    AppConstants.TeaConfiguration.TEA_GRADE -> response =
                        dashboardRepository.deleteTeaGradeItem(idBody)

                    AppConstants.TeaConfiguration.TEA_QUALITY -> response =
                        dashboardRepository.deleteTeaQualityItem(idBody)

                    AppConstants.TeaConfiguration.TEA_TYPE -> response =
                        dashboardRepository.deleteTeaCuttingItem(idBody)

                    AppConstants.TeaConfiguration.TEA_COLOR -> response =
                        dashboardRepository.deleteTeaColourItem(idBody)

                    AppConstants.TeaConfiguration.TEA_DENSITY -> response =
                        dashboardRepository.deleteTeaDensityItem(idBody)

                    AppConstants.TeaConfiguration.TEA_PRODUCT_PREFERENCE -> response =
                        dashboardRepository.deleteProductPreferenceItem(idBody)

                    AppConstants.TeaConfiguration.TEA_INWARD_BAG_TYPE -> response =
                        dashboardRepository.deleteTeaInwardBagTypeItem(idBody)

                    AppConstants.TeaConfiguration.TEA_GARDEN -> response =
                        dashboardRepository.deleteTeaGarden(idBody)

                    AppConstants.TeaConfiguration.TEA_SEASON -> response =
                        dashboardRepository.deleteTeaSeason(idBody)
                }
                withContext(Dispatchers.Main) {
                    deleteConfigurationItem.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun storeConfigurationItemPosition(data: String, type: Int) {
        val dataBody: RequestBody = AppUtils.getRequestBody(data)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: BaseResponse? = null;
                when (type) {
                    AppConstants.TeaConfiguration.TEA_GARDEN_AREA -> response =
                        dashboardRepository.storeGardenAreaPosition(dataBody)

                    AppConstants.TeaConfiguration.LEAF_TYPE -> response =
                        dashboardRepository.storeLeafTypePosition(dataBody)

                    AppConstants.TeaConfiguration.TEA_GRADE -> response =
                        dashboardRepository.storeTeaGradePosition(dataBody)

                    AppConstants.TeaConfiguration.TEA_QUALITY -> response =
                        dashboardRepository.storeTeaQualityPosition(dataBody)

                    AppConstants.TeaConfiguration.TEA_TYPE -> response =
                        dashboardRepository.storeTeaCuttingPosition(dataBody)

                    AppConstants.TeaConfiguration.TEA_COLOR -> response =
                        dashboardRepository.storeTeaColourPosition(dataBody)

                    AppConstants.TeaConfiguration.TEA_DENSITY -> response =
                        dashboardRepository.storeTeaDensityPosition(dataBody)

                    AppConstants.TeaConfiguration.TEA_PRODUCT_PREFERENCE -> response =
                        dashboardRepository.storeProductPreferencePosition(dataBody)

                    AppConstants.TeaConfiguration.TEA_INWARD_BAG_TYPE -> response =
                        dashboardRepository.storeTeaInwardBagTypePosition(dataBody)

                    AppConstants.TeaConfiguration.TEA_GARDEN -> response =
                        dashboardRepository.storeTeaGardensPosition(dataBody)

                    AppConstants.TeaConfiguration.TEA_SEASON -> response =
                        dashboardRepository.storeTeaSeasonsPosition(dataBody)
                }
                withContext(Dispatchers.Main) {
                    storeConfigurationItemPosition.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun storeTeaGardenItem(
        id: String,
        name: String,
        status: Int,
        leafTypeId: String,
        gardenAreaId: String,
        type: Int
    ) {
        val idBody: RequestBody = AppUtils.getRequestBody(id)
        val nameBody: RequestBody = AppUtils.getRequestBody(name)
        val statusBody: RequestBody = AppUtils.getRequestBody(status.toString())
        val leafTypeIdBody: RequestBody = AppUtils.getRequestBody(leafTypeId)
        val gardenAreaIdBody: RequestBody = AppUtils.getRequestBody(gardenAreaId)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: BaseResponse = dashboardRepository.storeTeaGarden(
                    idBody,
                    nameBody,
                    statusBody,
                    leafTypeIdBody,
                    gardenAreaIdBody
                );
                withContext(Dispatchers.Main) {
                    storeConfigurationItem.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getTeaGardenConfigurationResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = dashboardRepository.getTeaGardenConfiguration()
                withContext(Dispatchers.Main) {
                    teaGardenConfigurationResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getTeaSeasonConfigurationResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = dashboardRepository.getTeaSeasonConfiguration()
                withContext(Dispatchers.Main) {
                    teaSeasonConfigurationResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun storeTeaSeasonItem(info: ConfigurationItemInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: BaseResponse = dashboardRepository.storeTeaSeason(info);
                withContext(Dispatchers.Main) {
                    storeConfigurationItem.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getTeaSampleList(
        limit: Int,
        offset: Int,
        search: String,
        filters: String,
        startDate: String,
        endDate: String
    ) {
        val limitBody: RequestBody = AppUtils.getRequestBody(limit.toString())
        val offsetBody: RequestBody = AppUtils.getRequestBody(offset.toString())
        val searchBody: RequestBody = AppUtils.getRequestBody(search)
        val filtersBody: RequestBody = AppUtils.getRequestBody(filters)
        val startDateBody: RequestBody = AppUtils.getRequestBody(startDate)
        val endDateBody: RequestBody = AppUtils.getRequestBody(endDate)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: TeaSampleListResponse =
                    dashboardRepository.getTeaSampleList(
                        limitBody,
                        offsetBody,
                        searchBody,
                        filtersBody,
                        startDateBody,
                        endDateBody
                    )
                withContext(Dispatchers.Main) {
                    mTeaSampleListResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getTeaSampleConfigurationResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = dashboardRepository.getTeaSampleConfiguration()
                withContext(Dispatchers.Main) {
                    teaSampleConfigurationResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun storeTeaSampleResponse(info: TeaSampleInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: BaseResponse = dashboardRepository.storeTeaSample(info);
                withContext(Dispatchers.Main) {
                    storeTeaSampleResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun storeTeaSampleTestingResponse(info: TeaSampleTestingInfo) {
        val id: RequestBody = AppUtils.getRequestBody(info.id!!)
        val lu_tea_personal_grade_id: RequestBody =
            AppUtils.getRequestBody(info.lu_tea_personal_grade_id!!)
        val lu_tea_cutting_id: RequestBody = AppUtils.getRequestBody(info.lu_tea_cutting_id!!)
        val lu_tea_colour_id: RequestBody = AppUtils.getRequestBody(info.lu_tea_colour_id!!)
        val lu_tea_density_id: RequestBody = AppUtils.getRequestBody(info.lu_tea_density_id!!)
        val lu_tea_source_level_1_id: RequestBody =
            AppUtils.getRequestBody(info.lu_tea_source_level_1_id!!)
        val lu_tea_source_level_2_id: RequestBody =
            AppUtils.getRequestBody(info.lu_tea_source_level_2_id!!)
        val lu_tea_source_level_3_id: RequestBody =
            AppUtils.getRequestBody(info.lu_tea_source_level_3_id!!)
        val lu_tea_season_detail_id: RequestBody =
            AppUtils.getRequestBody(info.lu_tea_season_id!!)
        val our_quality_id: RequestBody = AppUtils.getRequestBody(info.quality_id!!)
        val lu_tea_product_preference_id: RequestBody =
            AppUtils.getRequestBody(info.lu_tea_product_preference_id!!)
        val manufacturer_date: RequestBody = AppUtils.getRequestBody(info.manufacturer_date!!)
        val note: RequestBody = AppUtils.getRequestBody(info.note!!)
        val rating: RequestBody = AppUtils.getRequestBody(info.rating!!)

        var image: MultipartBody.Part? = null
        if (!StringHelper.isEmpty(info.file) && !info.file!!.startsWith("http")) {
            val file = File(info.file)
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            image = MultipartBody.Part.createFormData("file", file.name, requestBody)
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: BaseResponse = dashboardRepository.storeTeaSampleTesting(
                    id,
                    lu_tea_personal_grade_id,
                    lu_tea_cutting_id,
                    lu_tea_colour_id,
                    lu_tea_density_id,
                    lu_tea_source_level_1_id,
                    lu_tea_source_level_2_id,
                    lu_tea_source_level_3_id,
                    lu_tea_season_detail_id,
                    our_quality_id,
                    lu_tea_product_preference_id,
                    manufacturer_date,
                    note,
                    rating,
                    image
                )
                withContext(Dispatchers.Main) {
                    storeTeaSampleTestingResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getAvailableTeaSampleList(search: String, startDate: String, endDate: String) {
        val searchBody: RequestBody = AppUtils.getRequestBody(search)
        val startDateBody: RequestBody = AppUtils.getRequestBody(startDate)
        val endDateBody: RequestBody = AppUtils.getRequestBody(endDate)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: AvailableTeaSampleListResponse =
                    dashboardRepository.availableTeaSampleList(searchBody,startDateBody,endDateBody)
                withContext(Dispatchers.Main) {
                    mAvailableTeaSampleListResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getAvailableTeaSampleConfigurationResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: AvailableTeaSampleConfigurationResponse =
                    dashboardRepository.getAvailableTeaSampleConfiguration()
                withContext(Dispatchers.Main) {
                    mAvailableTeaSampleConfigurationResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun storeTeaConfirmation(vendorId: String, date: String, imagePath: String, records: String) {
        val vendorIdBody: RequestBody = AppUtils.getRequestBody(vendorId)
        val dateBody: RequestBody = AppUtils.getRequestBody(date)
        val recordsBody: RequestBody = AppUtils.getRequestBody(records)
        var image: MultipartBody.Part? = null
        if (!StringHelper.isEmpty(imagePath) && !imagePath.startsWith("http")) {
            val file = File(imagePath)
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            image = MultipartBody.Part.createFormData("file", file.name, requestBody)
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: BaseResponse =
                    dashboardRepository.storeTeaConfirmation(
                        vendorIdBody,
                        dateBody,
                        recordsBody,
                        image
                    )
                withContext(Dispatchers.Main) {
                    storeAvailableTeaSample.value = response
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                traceErrorException(e)
            } catch (e: CancellationException) {
                e.printStackTrace()
                traceErrorException(e)
            } catch (e: Exception) {
                e.printStackTrace()
                traceErrorException(e)
            }
        }
    }

    fun getTeaConfirmationList(limit: Int, offset: Int, search: String) {
        val limitBody: RequestBody = AppUtils.getRequestBody(limit.toString())
        val offsetBody: RequestBody = AppUtils.getRequestBody(offset.toString())
        val searchBody: RequestBody = AppUtils.getRequestBody(search)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: AvailableTeaSampleListResponse =
                    dashboardRepository.getTeaConfirmationList(limitBody, offsetBody, searchBody)
                withContext(Dispatchers.Main) {
                    mAvailableTeaSampleListResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getTeaConfirmationGradeList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: AvailableTeaSampleListResponse =
                    dashboardRepository.getTeaConfirmationGradeList()
                withContext(Dispatchers.Main) {
                    mAvailableTeaSampleListResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getTeaSourceConfigurationList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: TeaSourceLevelListResponse =
                    dashboardRepository.getTeaSourcesConfiguration()
                withContext(Dispatchers.Main) {
                    teaSourceLevelListResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun storeTeaSourceResponse(info: TeaSourceLevelInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: BaseResponse = dashboardRepository.storeTeaSource(info);
                withContext(Dispatchers.Main) {
                    editDeleteTeaSource.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun deleteTeaSource(id: String) {
        val idBody: RequestBody = AppUtils.getRequestBody(id)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: BaseResponse =
                    dashboardRepository.deleteTeaSource(idBody)
                withContext(Dispatchers.Main) {
                    editDeleteTeaSource.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun getTeaTestedDataList(limit: Int, offset: Int, search: String) {
        val limitBody: RequestBody = AppUtils.getRequestBody(limit.toString())
        val offsetBody: RequestBody = AppUtils.getRequestBody(offset.toString())
        val searchBody: RequestBody = AppUtils.getRequestBody(search)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: TeaTestedSampleListResponse =
                    dashboardRepository.getTeaTestedDataList(limitBody, offsetBody, searchBody)
                withContext(Dispatchers.Main) {
                    mTeaTestedSampleListResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun teaTestingDataDetails(id: String) {
        val idBody: RequestBody = AppUtils.getRequestBody(id)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: TeaTestedSampleDetailsResponse =
                    dashboardRepository.teaTestingDataDetails(idBody)
                withContext(Dispatchers.Main) {
                    mTeaTestedSampleDetailsResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

    fun storeTeaTestedSampleResponse(info: TeaSampleTestingInfo) {
        val id: RequestBody = AppUtils.getRequestBody(info.id!!)
        val lu_tea_personal_grade_id: RequestBody =
            AppUtils.getRequestBody(info.lu_tea_personal_grade_id!!)
        val lu_tea_cutting_id: RequestBody = AppUtils.getRequestBody(info.lu_tea_cutting_id!!)
        val lu_tea_colour_id: RequestBody = AppUtils.getRequestBody(info.lu_tea_colour_id!!)
        val lu_tea_density_id: RequestBody = AppUtils.getRequestBody(info.lu_tea_density_id!!)
        val lu_tea_source_level_1_id: RequestBody =
            AppUtils.getRequestBody(info.lu_tea_source_level_1_id!!)
        val lu_tea_source_level_2_id: RequestBody =
            AppUtils.getRequestBody(info.lu_tea_source_level_2_id!!)
        val lu_tea_source_level_3_id: RequestBody =
            AppUtils.getRequestBody(info.lu_tea_source_level_3_id!!)
        val lu_tea_season_detail_id: RequestBody =
            AppUtils.getRequestBody(info.lu_tea_season_id!!)
        val our_quality_id: RequestBody = AppUtils.getRequestBody(info.quality_id!!)
        val lu_tea_product_preference_id: RequestBody =
            AppUtils.getRequestBody(info.lu_tea_product_preference_id!!)
        val manufacturer_date: RequestBody = AppUtils.getRequestBody(info.manufacturer_date!!)
        val note: RequestBody = AppUtils.getRequestBody(info.note!!)
        val rating: RequestBody = AppUtils.getRequestBody(info.rating!!)

        val vendor_id: RequestBody = AppUtils.getRequestBody(info.vendor_id!!)
        val lu_garden_id: RequestBody = AppUtils.getRequestBody(info.lu_garden_id!!)
        val lu_tea_grade_id: RequestBody = AppUtils.getRequestBody(info.lu_tea_grade_id!!)
        val bag: RequestBody = AppUtils.getRequestBody(info.bag!!)
        val weight: RequestBody = AppUtils.getRequestBody(info.weight!!)
        val rate: RequestBody = AppUtils.getRequestBody(info.rate!!)

        var image: MultipartBody.Part? = null
        if (!StringHelper.isEmpty(info.file) && !info.file!!.startsWith("http")) {
            val file = File(info.file)
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            image = MultipartBody.Part.createFormData("file", file.name, requestBody)
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: BaseResponse = dashboardRepository.storeTeaTestedSample(
                    id,
                    lu_tea_personal_grade_id,
                    lu_tea_cutting_id,
                    lu_tea_colour_id,
                    lu_tea_density_id,
                    lu_tea_source_level_1_id,
                    lu_tea_source_level_2_id,
                    lu_tea_source_level_3_id,
                    lu_tea_season_detail_id,
                    our_quality_id,
                    lu_tea_product_preference_id,
                    manufacturer_date,
                    note,
                    rating,
                    image,
                    vendor_id,
                    lu_garden_id,
                    lu_tea_grade_id,
                    bag,
                    weight,
                    rate
                )
                withContext(Dispatchers.Main) {
                    storeTeaSampleTestingResponse.value = response
                }
            } catch (e: JSONException) {
                traceErrorException(e)
            } catch (e: CancellationException) {
                traceErrorException(e)
            } catch (e: Exception) {
                traceErrorException(e)
            }
        }
    }

}