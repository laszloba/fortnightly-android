package hu.laszloba.fortnightly.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.laszloba.fortnightly.model.ArticlePresentationModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor() : ViewModel() {

    private val _viewState = MutableLiveData<ArticleViewState>()
    val viewState: LiveData<ArticleViewState>
        get() = _viewState

    init {
        _viewState.value = Loading
    }

    fun loadArticle(articleId: Long) {
        viewModelScope.launch {
            _viewState.value = Loading

            delay(500L)

            // TODO Get from data source
            _viewState.value = ArticleLoaded(
                ArticlePresentationModel(
                    title = "Apple's new augmented reality exec shows how important the tech is",
                    urlToImage = "https://via.placeholder.com/700x500/1A237E",
                    description = "Bloomberg points out that an executive formerly in charge of i" +
                        "Phone marketing for carriers, Frank Casanova, has a new title: Senio" +
                        "r Director, Worldwide Product Marketing at Apple Augmented Reality. " +
                        "While Google is dipping its toe into using AR to enhance Go...[+1061" +
                        " chars]"
                )
            )
        }
    }
}
