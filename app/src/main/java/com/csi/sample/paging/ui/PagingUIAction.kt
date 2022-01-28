package com.csi.sample.paging.ui

sealed class PagingUIAction {
    data class Search(val query : String) : PagingUIAction()
    data class Scroll(val currentQuery: String) : PagingUIAction()
}
