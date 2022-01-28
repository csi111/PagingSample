package com.csi.sample.paging.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubRepoResponse(
    @SerialName("total_count") val total: Int = 0,
    @SerialName("items") val items: List<GithubRepoDto> = emptyList(),
    @SerialName("next_page") val nextPage: Int?
)
