package com.unjhawalateaadmin.dashboard.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unjhawalateaadmin.common.data.model.BaseResponse
import com.unjhawalateaadmin.common.utils.traceErrorException
import com.unjhawalateaadmin.dashboard.data.model.*
import com.unjhawalateaadmin.dashboard.data.repository.DashboardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import java.util.concurrent.CancellationException

class DashboardViewModel(private val dashboardRepository: DashboardRepository) :
    ViewModel() {
    val baseResponse = MutableLiveData<BaseResponse>()
    val dashboardResponse = MutableLiveData<DashboardResponse>()
    val privacyPolicyResponse = MutableLiveData<PrivacyPolicyResponse>()
    val acceptTermsConditionsResponse = MutableLiveData<BaseResponse>()

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

}