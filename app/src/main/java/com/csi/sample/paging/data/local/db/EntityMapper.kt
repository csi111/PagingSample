package com.csi.sample.paging.data.local.db

import com.csi.sample.paging.domain.GithubRepo
import com.csi.sample.paging.domain.Key

fun GithubRepoKeyEntity.map(): Key = Key(
    id, prevKey, nextKey
)

fun Key.toEntity(): GithubRepoKeyEntity = GithubRepoKeyEntity(
    id, prevKey, nextKey
)

fun GithubRepoEntity.map(): GithubRepo = GithubRepo(
    id, name, fullName, description, url, stars, forks, language
)

fun GithubRepo.toEntity(): GithubRepoEntity = GithubRepoEntity(
    id, name, fullName, description, url, stars, forks, language
)