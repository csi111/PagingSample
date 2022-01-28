package com.csi.sample.paging.ui

import com.csi.sample.paging.ui.StringSet.DEFAULT_SEARCH_KEYWORD

data class PagingUIState(
    val query: String = DEFAULT_SEARCH_KEYWORD,
    val lastQueryScrolled: String = DEFAULT_SEARCH_KEYWORD,
    val hasNotScrolledForCurrentSearch: Boolean = false
)
