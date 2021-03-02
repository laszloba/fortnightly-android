package hu.laszloba.fortnightly.ui.list

import hu.laszloba.fortnightly.model.NewsListItemPresentationModel

sealed class NewsListViewState

object Loading : NewsListViewState()

object Error : NewsListViewState()

data class NewsListLoaded(
    val newsList: List<NewsListItemPresentationModel>
) : NewsListViewState()
