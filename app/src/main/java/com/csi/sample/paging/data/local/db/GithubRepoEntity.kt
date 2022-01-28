package com.csi.sample.paging.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_repo")
class GithubRepoEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val url: String,
    val stars: Int,
    val forks: Int,
    val language: String?
)