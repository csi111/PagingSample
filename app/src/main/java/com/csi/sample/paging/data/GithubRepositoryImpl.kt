package com.csi.sample.paging.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.csi.sample.paging.data.local.GithubLocalDataSource
import com.csi.sample.paging.data.local.db.GithubRepoEntity
import com.csi.sample.paging.data.local.db.PagingDatabase
import com.csi.sample.paging.data.local.db.map
import com.csi.sample.paging.data.remote.GithubRemoteDataSource
import com.csi.sample.paging.domain.GithubRepo
import com.csi.sample.paging.domain.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
class GithubRepositoryImpl @Inject constructor(
    private val localDataSource: GithubLocalDataSource,
    private val remoteDataSource: GithubRemoteDataSource
) : GithubRepository {

    companion object {
        private const val PAGE_SIZE = 30
    }

    override fun getSearchResult(query: String): Flow<PagingData<GithubRepoEntity>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = GithubRemoteMediator(query, remoteDataSource, localDataSource),
            pagingSourceFactory = {
                localDataSource.getPagingSource(query)
            }
        ).flow
    }
}