package com.csi.sample.paging.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class GithubRepoKeyEntity(
    @PrimaryKey val id: Long,
    val prevKey: Int?,
    val nextKey: Int?
)