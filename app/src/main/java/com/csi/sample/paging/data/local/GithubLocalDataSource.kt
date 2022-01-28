package com.csi.sample.paging.data.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.csi.sample.paging.data.local.db.GithubRepoEntity
import com.csi.sample.paging.data.local.db.PagingDatabase
import com.csi.sample.paging.data.local.db.map
import com.csi.sample.paging.data.local.db.toEntity
import com.csi.sample.paging.domain.GithubRepo
import com.csi.sample.paging.domain.Key
import javax.inject.Inject

class GithubLocalDataSource @Inject constructor(private val database: PagingDatabase) {

    private val githubRepoDao = database.githubRepoDao()
    private val githubRepoKeyDao = database.githubRepoKeyDao()

    suspend fun getKey(id: Long): Key {
        return githubRepoKeyDao.remoteKeysRepoId(id)?.map() ?: Key.EMPTY
    }

    fun getPagingSource(query: String): PagingSource<Int, GithubRepoEntity> {
        val dbQuery = "%${query.replace(' ', '%')}%"
        return githubRepoDao.reposByName(dbQuery)
    }

    suspend fun updateRepositories(isRefreshed: Boolean, list: List<GithubRepo>, curPage: Int) {
        database.withTransaction {
            // clear all tables in the database
            if (isRefreshed) {
                githubRepoKeyDao.clearRemoteKeys()
                githubRepoDao.clearRepos()
            }
            val prevKey = if (curPage == 1) null else curPage - 1
            val nextKey = if (list.isEmpty()) null else curPage + 1
            val keys = list.map {
                Key(id = it.id, prevKey = prevKey, nextKey = nextKey).toEntity()
            }
            githubRepoKeyDao.insertAll(keys)
            githubRepoDao.insertAll(list.map { it.toEntity() })
        }
    }
}