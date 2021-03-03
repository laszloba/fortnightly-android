package hu.laszloba.fortnightly.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.laszloba.fortnightly.data.mapper.database.topresentation.ArticleTupleToPresentationModelMapper
import hu.laszloba.fortnightly.database.NewsDao
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val newsDao: NewsDao,
    private val articleTupleToPresentationModelMapper: ArticleTupleToPresentationModelMapper
) : ViewModel() {

    private val _viewState = MutableLiveData<ArticleViewState>()
    val viewState: LiveData<ArticleViewState>
        get() = _viewState

    init {
        _viewState.value = Loading
    }

    fun loadArticle(articleId: Long) {
        viewModelScope.launch {
            _viewState.value = Loading

            val articleTuple = newsDao.getArticle(articleId)

            _viewState.value = if (articleTuple != null)
                ArticleLoaded(
                    articleTupleToPresentationModelMapper
                        .map(articleTuple)
                )
            else
                Error
        }
    }
}
