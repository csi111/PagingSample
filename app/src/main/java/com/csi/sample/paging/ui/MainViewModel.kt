package com.csi.sample.paging.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.csi.sample.paging.data.local.db.map
import com.csi.sample.paging.domain.GithubRepository
import com.csi.sample.paging.ui.StringSet.DEFAULT_SEARCH_KEYWORD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val githubRepository: GithubRepository) :
    ViewModel() {

    val state: StateFlow<PagingUIState>

    val pagingFlow: Flow<PagingData<PagingUIModel>>

    val accept: (PagingUIAction) -> Unit

    init {
        val actionStateFlow = MutableSharedFlow<PagingUIAction>()
        val searches = actionStateFlow.filterIsInstance<PagingUIAction.Search>()
            .distinctUntilChanged()
            .onStart {
                emit(PagingUIAction.Search(DEFAULT_SEARCH_KEYWORD))
            }

        val queriesScrolled =
            actionStateFlow.filterIsInstance<PagingUIAction.Scroll>().distinctUntilChanged()
                .shareIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                    1
                ).onStart {
                    emit(PagingUIAction.Scroll(DEFAULT_SEARCH_KEYWORD))
                }

        pagingFlow = searches.flatMapLatest {
            githubRepository.getSearchResult(it.query)
                .map { pagingData ->
                    pagingData.map { PagingUIModel.GithubRepoItem(it.map()) }
                }.map { data ->
                    data.insertSeparators { before: PagingUIModel.GithubRepoItem?, after: PagingUIModel.GithubRepoItem? ->
                        if (after == null) {
                            return@insertSeparators null
                        }

                        if (before == null) {
                            return@insertSeparators PagingUIModel.SeparatorItem("${after.roundedStarCount}0.000+ stars")
                        }

                        if (before.roundedStarCount > after.roundedStarCount) {
                            if (after.roundedStarCount >= 1) {
                                PagingUIModel.SeparatorItem("${after.roundedStarCount}0.000+ stars")
                            } else {
                                PagingUIModel.SeparatorItem("< 10.000+ stars")
                            }
                        } else {
                            // no separator
                            null
                        }
                    }
                }
        }.cachedIn(viewModelScope)

        state = combine(searches, queriesScrolled, ::Pair).map { (search, scroll) ->
            PagingUIState(
                query = search.query,
                lastQueryScrolled = scroll.currentQuery,
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingUIState())

        accept = {
            viewModelScope.launch {
                actionStateFlow.emit(it)
            }
        }
    }

}