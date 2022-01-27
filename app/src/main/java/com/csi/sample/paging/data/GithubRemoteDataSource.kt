package com.csi.sample.paging.data

import com.csi.sample.paging.data.remote.GithubService
import javax.inject.Inject

class GithubRemoteDataSource @Inject constructor(
    private val githubService: GithubService
) {
}