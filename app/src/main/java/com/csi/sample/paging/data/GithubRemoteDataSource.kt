package com.csi.sample.paging.data

import com.csi.sample.paging.data.remote.GithubService
import com.csi.sample.paging.data.remote.GithubRepoDto
import javax.inject.Inject

class GithubRemoteDataSource @Inject constructor(private val githubService: GithubService) {

    suspend fun search(query: String, page: Int, limit: Int): List<GithubRepoDto> {
        val result = githubService.searchRepos(query, page, limit)

        return result.items
    }
}