package com.csi.sample.paging.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    companion object {
        @Provides
        fun provideJson(): Json {
            return Json {
                ignoreUnknownKeys = true
                isLenient = true
                coerceInputValues = true
            }
        }
    }
}