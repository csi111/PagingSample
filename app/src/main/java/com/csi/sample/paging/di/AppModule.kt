package com.csi.sample.paging.di

import androidx.paging.ExperimentalPagingApi
import com.csi.sample.paging.data.GithubRepositoryImpl
import com.csi.sample.paging.domain.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @ExperimentalPagingApi
    @Binds
    fun bindsGithubRepository(repository: GithubRepositoryImpl) : GithubRepository

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