package com.csi.sample.paging.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.csi.sample.paging.data.remote.GithubRepoDto
import javax.inject.Inject

@ExperimentalPagingApi
class GithubRemoteMediator @Inject constructor(private val githubRemoteDataSource: GithubRemoteDataSource) :
    RemoteMediator<Int, GithubRepoDto>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GithubRepoDto>
    ): MediatorResult {
        when (loadType) {
            LoadType.REFRESH -> TODO()
            LoadType.PREPEND -> TODO()
            LoadType.APPEND -> TODO()
        }
    }
}