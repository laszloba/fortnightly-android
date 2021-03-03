package hu.laszloba.fortnightly.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.laszloba.fortnightly.api.NewsService
import hu.laszloba.fortnightly.data.api.ArticleApiModel
import hu.laszloba.fortnightly.data.mapper.api.todatabase.NewsListItemApiModelToDataModelMapper
import hu.laszloba.fortnightly.data.mapper.database.topresentation.NewsListItemTupleToPresentationModelMapper
import hu.laszloba.fortnightly.database.NewsDao
import hu.laszloba.fortnightly.model.NewsListItemPresentationModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val newsService: NewsService,
    private val newsDao: NewsDao,
    private val newsListItemApiModelToDataModelMapper: NewsListItemApiModelToDataModelMapper,
    private val newsListItemTupleToPresentationModelMapper: NewsListItemTupleToPresentationModelMapper
) : ViewModel() {

    companion object {
        private const val STATUS_OK = "ok"
    }

    private val _viewState = MutableLiveData<NewsListViewState>()
    val viewState: LiveData<NewsListViewState>
        get() = _viewState

    init {
        _viewState.value = Loading
    }

    fun loadNewsList() {
        viewModelScope.launch {
            _viewState.value = Loading

            try {
                val newsListResponse = newsService.getTopHeadlines()

                if (newsListResponse.isSuccessful) {
                    val topHeadlinesApiModel = newsListResponse.body()

                    if (topHeadlinesApiModel != null) {
                        if (topHeadlinesApiModel.status == STATUS_OK) {
                            _viewState.value = NewsListLoaded(
                                saveAndReturnNewsList(topHeadlinesApiModel.articles)
                            )
                        } else {
                            _viewState.value =
                                Error(
                                    "Bad API status: ${topHeadlinesApiModel.status}" +
                                        ", message: ${topHeadlinesApiModel.message}"
                                )
                        }
                    } else {
                        _viewState.value = Error("Response body is empty")
                    }
                } else {
                    _viewState.value =
                        Error("Bad response: HTTP ${newsListResponse.code()}")
                }
            } catch (e: Exception) {
                _viewState.value = Error("Unknown error: ${e.message}")
            }
        }
    }

    private suspend fun saveAndReturnNewsList(
        articles: List<ArticleApiModel>
    ): List<NewsListItemPresentationModel> {
        with(newsDao) {
            clearArticles()
            insertArticles(newsListItemApiModelToDataModelMapper.map(articles))

            return getNews().mapIndexed { index, newsListItemTuple ->
                if (index == 0) {
                    newsListItemTupleToPresentationModelMapper.mapToLargeListItem(newsListItemTuple)
                } else {
                    newsListItemTupleToPresentationModelMapper.mapToSmallListItem(newsListItemTuple)
                }
            }
        }
    }
}
