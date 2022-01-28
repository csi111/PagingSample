package com.csi.sample.paging.domain

import androidx.paging.PagingData
import com.csi.sample.paging.data.remote.GithubRepoDto
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    fun getSearchResult(): Flow<PagingData<GithubRepoDto>>
}