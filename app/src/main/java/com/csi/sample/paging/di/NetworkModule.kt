package com.csi.sample.paging.di

import androidx.viewbinding.BuildConfig
import com.csi.sample.paging.data.remote.GithubService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {


    companion object {
        private const val GITHUB_API = "https://api.github.com/"

        @Provides
        @Singleton
        fun provideOkHttp(): OkHttpClient {
            return OkHttpClient
                .Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) Level.BASIC else Level.NONE
                })
                .build()

        }

        @ExperimentalSerializationApi
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
            return Retrofit
                .Builder()
                .baseUrl(GITHUB_API)
                .client(okHttpClient)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
        }


        @Provides
        fun provideGithubService(retrofit: Retrofit): GithubService {
            return retrofit.create(GithubService::class.java)
        }
    }
}