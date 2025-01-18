package com.example.seton.di

import android.content.Context
import com.example.seton.network.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object ApiModule {
//
//    private const val BASE_URL = "http://34.223.224.180:8080/"
//
//    @Singleton
//    @Provides
//    fun provideOkHttpClient(): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        return OkHttpClient.Builder()
//            .addInterceptor(AuthorizationInterceptor())
//            .addNetworkInterceptor(interceptor)
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideRetrofit(client: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//}

object RetrofitInstance {
    private const val BASE_URL = "http://34.223.224.180:8080/"

    fun getApiService(context: Context): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val tokenInterceptor = Interceptor { chain ->
            val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("token", null)
            val request = if (token != null) {
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            } else {
                chain.request()
            }
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}