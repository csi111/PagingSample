package com.csi.sample.paging.data.remote

import com.csi.sample.paging.data.local.db.GithubRepoKeyEntity
import com.csi.sample.paging.domain.GithubRepo
import com.csi.sample.paging.domain.Key


fun GithubRepoDto.map(): GithubRepo = GithubRepo(
    id, name, fullName, description, url, stars, forks, language
)