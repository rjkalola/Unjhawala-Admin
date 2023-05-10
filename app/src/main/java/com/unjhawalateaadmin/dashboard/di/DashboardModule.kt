package com.unjhawalateaadmin.dashboard.di

import com.unjhawalateaadmin.common.utils.CoroutineCallAdapterFactory
import com.unjhawalateaadmin.common.utils.VariantConfig
import com.unjhawalateaadmin.dashboard.data.remote.DashboardInterface
import com.unjhawalateaadmin.dashboard.data.repository.DashboardRepository
import com.unjhawalateaadmin.dashboard.data.repository.imp.DashboardRepositoryImp
import com.unjhawalateaadmin.dashboard.ui.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

var dashboardModule = module {
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(VariantConfig.serverBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create<DashboardInterface>()
    }

    single<DashboardRepository> { DashboardRepositoryImp(dashboardInterface = get()) }

    viewModel { DashboardViewModel(dashboardRepository = get()) }
}