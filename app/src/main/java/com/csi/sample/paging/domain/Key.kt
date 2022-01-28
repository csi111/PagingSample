package com.csi.sample.paging.domain

data class Key(
    val id: Long,
    val prevKey: Int?,
    val nextKey: Int?
) {
    companion object {
        val EMPTY = Key(id = 0, prevKey = null, nextKey = null)
    }
}
