package com.csi.sample.paging.ui

import com.csi.sample.paging.domain.GithubRepo

sealed class PagingUIModel {

    data class GithubRepoItem(val repo: GithubRepo) : PagingUIModel() {
        val roundedStarCount: Int
            get() = this.repo.stars / 10_000
    }

    data class SeparatorItem(val description: String) : PagingUIModel()
}
