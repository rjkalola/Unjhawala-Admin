package com.unjhawalateaadmin.authentication.di


import android.content.Context
import com.unjhawalateaadmin.MyApplication
import com.imateplus.utilities.utils.StringHelper
import com.unjhawalateaadmin.authentication.data.repository.AuthenticationRepository
import com.unjhawalateaadmin.authentication.data.repository.imp.AuthenticationRepositoryImp
import com.unjhawalateaadmin.authentication.ui.viewmodel.AuthenticationViewModel
import com.unjhawalateaadmin.common.utils.AppUtils
import com.unjhawalateaadmin.authentication.data.model.User
import com.unjhawalateaadmin.authentication.data.remote.AuthenticationInterface
import com.unjhawalateaadmin.common.utils.CoroutineCallAdapterFactory
import com.unjhawalateaadmin.common.utils.VariantConfig
import com.google.gson.GsonBuilder
import com.unjhawalateaadmin.common.utils.AppConstants
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File

var authenticationModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single { GsonBuilder().serializeNulls().create() }

    single {
        val cacheDir = File((get<Context>() as MyApplication).cacheDir, "http")
        val cache = Cache(
            cacheDir,
            10 * 1024 * 1024 // 10 MB
        )

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            try {
                val userInfo: User? = AppUtils.getUserPreference(MyApplication().getContext())
                if (userInfo != null && !StringHelper.isEmpty(userInfo.api_token)) {
                    requestBuilder.addHeader("Authorization", "Bearer " + userInfo.api_token)
                }

                if (!StringHelper.isEmpty(AppUtils.getDeviceId(MyApplication().getContext()))) {
                    requestBuilder.addHeader(
                        "device-id",
                        AppUtils.getDeviceId(MyApplication().getContext())!!
                    )
                }

//                requestBuilder.addHeader(
//                    "type",
//                    AppConstants.USER_TYPE.toString()
//                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val request = requestBuilder.build()
            chain.proceed(request)
        }
            .cache(cache)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    // Provide Retrofit
    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(VariantConfig.serverBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create<AuthenticationInterface>()
    }

    single<AuthenticationRepository> { AuthenticationRepositoryImp(authenticationInterface = get()) }

    viewModel { AuthenticationViewModel(authenticationRepository = get()) }
}