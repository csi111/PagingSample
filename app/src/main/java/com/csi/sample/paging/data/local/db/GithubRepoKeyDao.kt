package com.csi.sample.paging.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GithubRepoKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<GithubRepoKeyEntity>)

    @Query("SELECT * FROM remote_keys WHERE id = :repoId")
    suspend fun remoteKeysRepoId(repoId: Long): GithubRepoKeyEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}