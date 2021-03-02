package hu.laszloba.fortnightly.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.laszloba.fortnightly.model.LargeNewsListItemPresentationModel
import hu.laszloba.fortnightly.model.SmallNewsListItemPresentationModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor() : ViewModel() {

    private val _viewState = MutableLiveData<NewsListViewState>()
    val viewState: LiveData<NewsListViewState>
        get() = _viewState

    init {
        _viewState.value = Loading
    }

    fun loadNewsList() {
        viewModelScope.launch {
            _viewState.value = Loading

            delay(500L)

            // TODO Get from data source
            _viewState.value = NewsListLoaded(
                listOf(
                    LargeNewsListItemPresentationModel(
                        id = 1,
                        title = "Mars One dreams plummet back to Earth as company goes bankrupt",
                        urlToImage = "https://upload.wikimedia.org/wikipedia/commons/a/ae/Warwick_Castle_May_2016.jpg",
                        timeAgo = "1H"
                    ),
                    SmallNewsListItemPresentationModel(
                        id = 2,
                        title = "Microsoft helps LinkedIn launch its first live video streaming service",
                        urlToImage = "https://upload.wikimedia.org/wikipedia/commons/5/50/Bodiam-castle-10My8-1197.jpg",
                        timeAgo = "2H"
                    ),
                    SmallNewsListItemPresentationModel(
                        id = 3,
                        title = "Futures point to a triple-digit gain for the Dow after tentative deal to avoid government shutdown",
                        urlToImage = "https://upload.wikimedia.org/wikipedia/commons/8/8e/Hever_Castle_2014_06_20_1.jpg",
                        timeAgo = "3H"
                    )
                )
            )
        }
    }
}
