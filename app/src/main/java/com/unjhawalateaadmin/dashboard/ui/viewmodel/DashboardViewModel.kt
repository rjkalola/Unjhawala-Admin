package com.unjhawalateaadmin.dashboard.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.utils.AppConstants
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.common.utils.traceErrorException
import com.unjhawalateaadmin.dashboard.data.model.*
import com.unjhawalateaadmin.dashboard.data.repository.DashboardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import org.json.JSONException
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

    fun storeTeaGardenItem(id: String, name: String, status: Int,leafTypeId:String,gardenAreaId:String, type: Int) {
        val idBody: RequestBody = AppUtils.getRequestBody(id)
        val nameBody: RequestBody = AppUtils.getRequestBody(name)
        val statusBody: RequestBody = AppUtils.getRequestBody(status.toString())
        val leafTypeIdBody: RequestBody = AppUtils.getRequestBody(leafTypeId)
        val gardenAreaIdBody: RequestBody = AppUtils.getRequestBody(gardenAreaId)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response: BaseResponse = dashboardRepository.storeTeaGarden(idBody, nameBody, statusBody,leafTypeIdBody,gardenAreaIdBody);
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

}