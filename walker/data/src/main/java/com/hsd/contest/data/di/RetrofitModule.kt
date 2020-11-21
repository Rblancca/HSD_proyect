package com.hsd.contest.data.di

import com.hsd.contest.data.repository.RoutesService
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val connectTimeout = 60L
private const val readTimeout = 60L
const val BASE_URL = "https://apirtod.dipucadiz.es/"

class RetrofitModule {
    fun getModule() = module {

        single { createOkHttpClient() }
        single<RoutesService> { createApiClient(get()) }

    }


    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .protocols(listOf(Protocol.HTTP_1_1))
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .build()
    }

    private inline fun <reified T> createApiClient(
        okHttpClient: OkHttpClient,
        baseUrl: String = BASE_URL
    ): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(T::class.java)
    }
}