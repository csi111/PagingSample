package com.csi.sample.paging.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GithubRepoEntity::class, GithubRepoKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PagingDatabase : RoomDatabase() {

    abstract fun githubRepoDao(): GithubRepoDao

    abstract fun githubRepoKeyDao(): GithubRepoKeyDao
}