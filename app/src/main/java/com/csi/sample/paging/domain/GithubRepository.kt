package com.csi.sample.paging.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    fun getSearchResult(): Flow<PagingData<GithubRepo>>
}