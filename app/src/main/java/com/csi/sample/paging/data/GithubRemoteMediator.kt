package com.csi.sample.paging.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.csi.sample.paging.data.local.GithubLocalDataSource
import com.csi.sample.paging.data.local.db.GithubRepoEntity
import com.csi.sample.paging.data.local.db.PagingDatabase
import com.csi.sample.paging.data.local.db.toEntity
import com.csi.sample.paging.data.remote.GithubRemoteDataSource
import com.csi.sample.paging.data.remote.map
import com.csi.sample.paging.domain.Key

@ExperimentalPagingApi
class GithubRemoteMediator(
    private val query: String,
    private val remoteDataSource: GithubRemoteDataSource,
    private val localDataSource: GithubLocalDataSource
) :
    RemoteMediator<Int, GithubRepoEntity>() {

    companion object {
        private const val GITHUB_STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GithubRepoEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val key = getRemoteKeyClosestToCurrentPosition(state)
                key?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val key = getRemoteKeyForFirstItem(state)
                key?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = key != null)
            }
            LoadType.APPEND -> {
                val key = getRemoteKeyForLastItem(state)
                key?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = key != null)
            }
        }

        return loadData(loadType, page, state)
    }

    private suspend fun loadData(
        loadType: LoadType,
        page: Int,
        state: PagingState<Int, GithubRepoEntity>
    ): MediatorResult {
        val result = remoteDataSource.search(query, page, state.config.pageSize).map {
            it.map()
        }
        val endOfPaginationReached = result.isEmpty()
        localDataSource.updateRepositories(
            isRefreshed = loadType == LoadType.REFRESH,
            list = result,
            curPage = page
        )
        return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GithubRepoEntity>): Key? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                localDataSource.getKey(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GithubRepoEntity>): Key? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                localDataSource.getKey(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, GithubRepoEntity>
    ): Key? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                localDataSource.getKey(repoId)
            }
        }
    }
}