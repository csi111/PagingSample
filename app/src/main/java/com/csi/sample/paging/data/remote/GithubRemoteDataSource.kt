package com.csi.sample.paging.data.remote

import android.util.Log
import javax.inject.Inject

class GithubRemoteDataSource @Inject constructor(private val githubService: GithubService) {

    suspend fun search(query: String, page: Int, limit: Int): List<GithubRepoDto> {
        val result = kotlin.runCatching {
            githubService.searchRepos(query, page, limit)
        }.onFailure {
            it.printStackTrace()
            Log.e("TEST", "Huu", it)
        }.onSuccess {
            Log.d("TEST", it.toString())
        }.getOrNull()

        return result?.items ?: emptyList()
    }
}